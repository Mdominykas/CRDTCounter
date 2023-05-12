class CRDTCounter {
    private var addition: Int
    private var subtraction: Int
    constructor(){
        addition = 0
        subtraction = 0
    }
    fun get(): Int {
        return addition - subtraction;
    }
    fun inc(){
        addition++;
    }
    fun dec(){
        subtraction++;
    }
}