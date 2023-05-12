fun main(args: Array<String>) {
    println("Hello World!")

    val counter = CRDTCounter()

    for(i in 0..9){
        if(i % 2 == 0){
            counter.inc()
        }
        else{
            counter.dec()
        }
    }
    println(counter.get())
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}