import kotlin.test.Test
import kotlin.test.assertEquals

class CRDTCounterTest{
    @Test
    fun additionSubtractionIsCorrect1(){
        val counter = CRDTCounter();
        for(i in 0..9){
            if(i % 2 == 0){
                counter.inc()
            }
            else{
                counter.dec()
            }
        }
        assertEquals(counter.get(), 0)
    }
    @Test
    fun additionSubtractionIsCorrect2(){
        val counter = CRDTCounter();
        for(i in 1..100)
            counter.inc()
        for(i in 1..50)
            counter.dec()
        assertEquals(counter.get(), 50)
    }

}