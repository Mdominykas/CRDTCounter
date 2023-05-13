/*
AskingValueMessage class is used to ask for other counterValues. When CRDTCounter receives it, it sends it's own counts to
the sender.
 */
data class AskingValueMessage(val sender: Int) : Message