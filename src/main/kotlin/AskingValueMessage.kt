/*
AskingValueMessage class is used to ask for other counterValues. When CRDTCounter receives it, it sends its own counts to
the sender.
 */
data class AskingValueMessage(val sender: Int) : Message