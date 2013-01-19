package agents;

/**
 * User: janus
 * Date: 12-11-03
 * Time: 22:08
 */
public class ConversationIds {
    // TODO uzupełnić enum
    private final String name;

    private ConversationIds(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
