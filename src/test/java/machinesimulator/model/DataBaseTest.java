package machinesimulator.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * User: janus
 * Date: 13-01-12
 * Time: 19:41
 */
public class DataBaseTest {
    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        // .configure() - configures settings from hibernate.cfg.xml
    }

    @After
    public void tearDown() throws Exception {
        if (sessionFactory != null)
            sessionFactory.close();
    }

    @Test
    public void shouldInsertMachineToDB() {
        Session session = sessionWithOpenTransaction();
        session.save(createMachine("firstMachine"));
        session.save(createMachine("secondMachine"));
        session.save(createMachine("thirdMachine"));
        closeSession(session);

        // read records from DB
        session = sessionWithOpenTransaction();
        List results = session.createQuery("from Machine").list();
        for (Machine machine : (List<Machine>) results) {
            System.out.println("Machine (" + machine.getId() + ": " + machine.getName() + ", " + machine.getDescription() + ")");
        }
        closeSession(session);

        // drop data from DB
        dropDataResultFromDB(results);
    }

    @Test
    public void shouldInsertParametersDict() {
        Session session = sessionWithOpenTransaction();
        session.save(createParameterDict("temp", "mensuration temperature"));
        session.save(createParameterDict("period", "mensuration period"));
        session.save(createParameterDict("coolantLevel", "mensuration coolant level"));
        closeSession(session);
        // read records from DB
        session = sessionWithOpenTransaction();
        List results = session.createQuery("from ParameterDict").list();
        for (ParameterDict parameterDict : (List<ParameterDict>) results) {
            System.out.println("ParameterDict (" + parameterDict.getId() + ": " + parameterDict.getName() + ", " + parameterDict.getDescription() + ")");
        }
        closeSession(session);

        // drop data from DB
        dropDataResultFromDB(results);
    }

    @Test
    public void shouldInsertParameters() {
        Session session = sessionWithOpenTransaction();
        session.save(createMachine("firstMachine"));
        session.save(createParameterDict("temp", "mensuration temperature"));
        session.save(createParameterDict("period", "mensuration period"));
        session.getTransaction().commit();

        // save parameters
        session.beginTransaction();
        Machine machine = (Machine) session.createQuery("from Machine").uniqueResult();
        List<ParameterDict> parameterDicts =
                (List<ParameterDict>) session.createQuery("from ParameterDict").list();
        session.save(createParameter(machine, parameterDicts.get(0), 100));
        session.save(createParameter(machine, parameterDicts.get(1), 200));
        closeSession(session);
        // read records from DB
        session = sessionWithOpenTransaction();
        List results = session.createQuery("from Parameter").list();
        for (Parameter parameter : (List<Parameter>) results)
            System.out.println("Parameter (" + parameter.getId() + ", " +
                parameter.getParameterDict().getName() + ", " +
                parameter.getMachine().getName() + ", " +
                parameter.getValue() + ")");
        closeSession(session);

        // drop data from DB
        dropDataResultFromDB(results);
    }

    @Test
    public void shouldInsertStatuses() {
        Session session = sessionWithOpenTransaction();
        session.save(createMachine("firstMachine"));
        session.save(createParameterDict("temp", "mensuration temperature"));
        session.save(createParameterDict("period", "mensuration period"));
        session.getTransaction().commit();

        session.beginTransaction();
        Machine machine = (Machine) session.createQuery("from Machine").uniqueResult();
        List<ParameterDict> parameterDicts =
                (List<ParameterDict>) session.createQuery("from ParameterDict").list();
        session.save(createStatus(machine, parameterDicts.get(0), false));
        session.save(createStatus(machine, parameterDicts.get(1), true));
        closeSession(session);

        // read records from DB
        session = sessionWithOpenTransaction();
        List results = session.createQuery("from MachineStatus").list();
        for (MachineStatus machineStatus : (List<MachineStatus>) results)
            System.out.println("MachineStatus (" + machineStatus.getId() + ", " +
                    machineStatus.getParameterDict().getName() + ", " +
                    machineStatus.getMachine().getName() + ", " +
                    machineStatus.isStatus() + ")");
        closeSession(session);

        // drop data from DB
        dropDataResultFromDB(results);
    }

    private MachineStatus createStatus(Machine machine, ParameterDict parameterDict, boolean status) {
        MachineStatus machineStatus = new MachineStatus();
        machineStatus.setMachine(machine);
        machineStatus.setParameterDict(parameterDict);
        machineStatus.setStatus(status);
        machineStatus.setUpdateDate(new Date());
        return machineStatus;
    }

    private Parameter createParameter(Machine machine, ParameterDict parameterDict, float value) {
        Parameter parameter = new Parameter();
        parameter.setMachine(machine);
        parameter.setParameterDict(parameterDict);
        parameter.setRegisterTime(new Date());
        parameter.setValue(value);
        return parameter;
    }

    private Session sessionWithOpenTransaction() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    private void closeSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    private void dropDataResultFromDB(List results) {
        Session session = sessionWithOpenTransaction();
        for (Object result : results)
            session.delete(result);
        closeSession(session);
    }

    private Machine createMachine(String name) {
        Machine machine = new Machine();
        machine.setName(name);
        machine.setDescription("This is " + name + " machine");
        machine.setOnTime(new Date());
        return machine;
    }

    private ParameterDict createParameterDict(String name, String description) {
        ParameterDict parameterDict = new ParameterDict();
        parameterDict.setName(name);
        parameterDict.setDescription(description);
        return parameterDict;
    }

}
