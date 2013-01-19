package machine;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: janus
 * Date: 12-09-10
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */
public class Machine {
    private boolean isRunning;
    private boolean isWork;
    private volatile Integer temp; // temperatura
    private DateTime beginTime;
    private Period onPeriod;
    private DateTime beginOperationTime;
    private Period operationPeriod; // czas obróbki
    private int coolantMin;
    private int coolantMax;
    private volatile Integer coolantLevel; // poziom chłodziwa w obrabiarce
    private int cuttingForce; // siła skrawania
    private int poziomDrgań; // poziom drgań

    private Thread tempDemon;
    private Thread coolantDemon;

    public Machine() {
        isWork = true;
        beginTime = new DateTime();
        operationPeriod = new Period();
        coolantLevel = 200;
        System.out.println("Maszyna rozpoczyna działanie (podpięcie do prądu)");
        // w konstruktorze powinny być odpalane wątki odpowiedzialne za ustawienie parametrów maszyny (temperatura, czas, poziom chłodziwa)
        // ustawić wartość poziomu chłodziwa (max, min, current)
    }

    public void start() {
        isRunning = true;
        System.out.println("Maszyna rozpoczyna skrawanie");
        runTempSettingDemon();
        runCoolantSettingDemon();
        beginOperationTime = new DateTime();
        // uruchamiane wątki odpowiedzialne za zliczanie czasu obróbki, siłę skrawania, poziom drgań, poziom chłodziwa
    }

    public void stop() {
        isRunning = false;
        tempDemon.interrupt();
        coolantDemon.interrupt();
        System.out.println("Maszyna kończy skrawanie");
        operationPeriod = operationPeriod.plus(new Period(beginOperationTime, new DateTime()));
        // zatrzymanie wątków uruchomionych w metodzie start
    }

    public void off() {
        if (isRunning)
            stop();
        onPeriod = new Period(beginTime, new DateTime());
        isWork = false;
        System.out.println("Maszyna kończy działanie (odłączenie od prądu)");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public boolean isWork() {
        return isWork;
    }


    public String getTemp() {
        if (temp != null)
            return temp.toString();
        else
            return null;
    }

    public String getPeriod() {
        if (onPeriod != null)
            return onPeriod.toStandardSeconds().toString();
        else
            return null;
    }

    public String getOperationPeriod() {
        if (operationPeriod != null)
            return operationPeriod.toStandardSeconds().toString();
        else
            return null;
    }

    public String getCoolantLevel() {
        if (coolantLevel != null)
            return coolantLevel.toString();
        else
            return null;
    }

    private void runTempSettingDemon() {
        tempDemon = new Thread(new TemperatureSettingDemon());
        tempDemon.setDaemon(true);
        tempDemon.start();
    }

    private void runCoolantSettingDemon() {
        coolantDemon = new Thread(new CoolantSettingDemon());
        coolantDemon.setDaemon(true);
        coolantDemon.start();
    }

    // 0. poświęcić do 30 minut na oszacowanie czasu pracy
    // 1. proste GUI do obsługi obrabiarki (on/off, start/stop)
    // 2. wyniki wrzucić na konsolę
    // 3. napisać agentów, którzy będą rejestrować dane parametry (to oni powinni na konsoli wyświetlać te parametry)
    // 4. dobrze byłoby gdyby program dawał obsługę włączenia/wyłączenia danego agenta

    class TemperatureSettingDemon implements Runnable {
        private Random generator;

        TemperatureSettingDemon() {
            super();
            generator = new Random();
            temp = 20;
        }

        public void run() {
            try {
                while(true) {
                    setTemperature();
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (InterruptedException e) {
                System.out.println("--- koniec ustawiania temperatury ---");
            }
        }

        private void setTemperature() {
            if (generator.nextInt(5) == 0) {
                temp += 1;
            } else if (generator.nextInt(5) == 4) {
                temp -= 1;
            }
        }
    }

    class CoolantSettingDemon implements Runnable {
        private Random generator;

        CoolantSettingDemon() {
            super();
            generator = new Random();
        }

        public void run() {
            try {
                while(true) {
                    setCoolant();
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (InterruptedException e) {
                System.out.println("--- koniec ustawiania poziomu chłodziwa ---");
            }
        }

        private void setCoolant() {
            int value = generator.nextInt(7);
            if (value == 0) {
                coolantLevel += 10;
            } else if (value == 6) {
                coolantLevel -= 15;
            }
        }
    }

}
