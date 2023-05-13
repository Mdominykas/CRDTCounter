import kotlin.test.Test
import kotlin.test.assertEquals

class SingleThreadedTests {
    @Test
    fun twoCountersAdditionCorrect(){
        val messaging = BetweenThreadMessaging()
        val counter1 = CRDTCounter(messaging)
        val counter2 = CRDTCounter(messaging)
        counter1.inc()
        counter2.inc()
        assertEquals(counter1.get(), 2)
        assertEquals(counter2.get(), 2)
    }
    @Test
    fun twoCountersAdditionAndSubtraction(){
        val messaging = BetweenThreadMessaging()
        val counter1 = CRDTCounter(messaging)
        val counter2 = CRDTCounter(messaging)
        counter1.inc()
        counter1.dec()
        counter2.inc()
        counter2.dec()
        counter2.inc()
        assertEquals(counter1.get(), 1)
        assertEquals(counter2.get(), 1)
    }

    @Test
    fun laterInitializedCounter(){
        val messaging = BetweenThreadMessaging()
        val counter1 = CRDTCounter(messaging)
        counter1.inc()
        counter1.inc()
        val counter2 = CRDTCounter(messaging)
        assertEquals(counter1.get(), 2)
        assertEquals(counter2.get(), 2)
        counter2.dec()
        assertEquals(counter1.get(), 1)
        assertEquals(counter2.get(), 1)
    }
}