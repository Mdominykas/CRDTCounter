import kotlin.test.Test
import kotlin.test.assertEquals

class SimpleThread(): Thread() {
    private lateinit var messaging: Messaging
    private lateinit var counter: CRDTCounter
    private var workLoad: Int = 0

    constructor(messaging: Messaging, workLoad: Int) : this() {
        this.messaging = messaging
        this.counter = CRDTCounter(this.messaging)
        this.workLoad = workLoad
    }
    public override fun run() {
        for(i in 0..2 * this.workLoad){
            if(i % 2 == 0)
                counter.inc()
            else{
                counter.dec()
            }
        }
    }

    public fun getCount() : Int{
        return counter.get()
    }
}
class MultiThreadedTests {
    @Test
    fun multipleParallelCounters(){
        val workLoads = arrayOf(10, 100, 1000, 10000, 100000)
        for(numberOfThreads in 3..5){
            for(workLoad in workLoads){
                testCounterOnMultipleThreads(numberOfThreads, workLoad)
            }
        }
    }

    private fun testCounterOnMultipleThreads(numberOfThreads: Int, workLoad: Int){
        val messaging = BetweenThreadMessaging()
        val threads = Array(numberOfThreads) { _ -> SimpleThread(messaging, workLoad) }
        for(i in 0 until numberOfThreads){
            threads[i].start()
        }
        for(i in 0 until numberOfThreads){
            threads[i].join()
        }
        for(i in 0 until numberOfThreads){
            assertEquals(threads[i].getCount(), numberOfThreads)
        }

    }

}