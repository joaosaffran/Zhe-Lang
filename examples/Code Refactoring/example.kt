import Zhe.lib.*

fun main(args: Array<String>) {
    val input: String = """
    if s != nil {
        for _ , x := range s {
        anything can go in here
        }
    }"""

    val runner = Runner()

    val FOR = regex("for") + many(string / ",", space) + ":= range " + string +
              " \\{" + 
              many(string, space) + 
               "\\}"

    val IF = regex("if ") + string + " != nil \\{" + 
            many(FOR, space) + 
            "}"
    
    runner.addEvent(IF,
    { vals ->
        // TODO: Implement a better model to handle the events

        val forVarStart = vals.indexOf("for")
        val forVarEnd = vals.indexOf(":= range ")
        val forVar1 = vals.subList(forVarStart + 1, forVarEnd).joinToString(separator = " ").trim()

        val forVar2 = vals.get(forVarEnd + 1)

        val ifVar = vals.get(1)

        val bodyEnd = vals.indexOfFirst { e -> e == "}"}
        val bodyInit = vals.indexOfLast { e -> e == " {"}

        val body = vals.subList(bodyInit + 1, bodyEnd).joinToString(separator = " ")


        if( ifVar == forVar2){
            println("Code Rewritten: ")
            println("for ${forVar1} := range ${ifVar} {\n\t${body}\n}")
        }
    })

    runner.invoke(input)
}