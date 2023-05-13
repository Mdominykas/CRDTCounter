/*
UpdatedValueMessage - message that counter sends out after updating its values
 */
data class UpdatedValueMessage(val senderId: Int, val newAdditionValue: Int, val newSubtractionValue: Int) : Message