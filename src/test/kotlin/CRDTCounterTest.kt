import kotlin.test.Test
import kotlin.test.assertEquals

class CRDTCounterTest {
    @Test
    fun testAdditionSubtractionIsCorrect1() {
        val counter = CRDTCounter(BetweenThreadMessaging())
        for (i in 0..9) {
            if (i % 2 == 0) {
                counter.inc()
            } else {
                counter.dec()
            }
        }
        assertEquals(counter.get(), 0)
    }

    @Test
    fun testAdditionSubtractionIsCorrect2() {
        val counter = CRDTCounter(BetweenThreadMessaging())
        for (i in 1..100)
            counter.inc()
        for (i in 1..50)
            counter.dec()
        assertEquals(counter.get(), 50)
    }

    @Test
    fun testInitialValueIsZero() {
        val messaging = BetweenThreadMessaging()
        val counter = CRDTCounter(messaging)

        assertEquals(counter.get(), 0)
    }
}