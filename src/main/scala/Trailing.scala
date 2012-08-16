object Trailing {
  private[this] val HelpText = """trailing [input]
    If no input is supplied, takes stdin as input.
    Returns 0 if there isn't any trailing whitespace, 1 otherwise, and prints out all
    of the locations of the trailing whitespace."""
  def main(args: Array[String]) {
    exitOnHelpPlea(args)
    val input = deduceInput(args)
    val badLines = filterBadLines(input)
    val valid = isValid(badLines)
    printBadLines(badLines)
    sys.exit(valid)
  }

  private[this] def printHelpText() {
    print(HelpText)
  }

  private[this] def printBadLines(badLines: Iterator[(String, Int)]): Unit =
    print((badLines map (line => "%d\t%s".format(line._2, line._1)) mkString ("\n")))

  private[this] def isValid(badLines: Iterator[(String, Int)]): Int =
    if (badLines.isEmpty) 0 else 1

  private[this] def filterBadLines(input: scala.io.BufferedSource): Iterator[(String, Int)] =
    input.getLines.zipWithIndex filter { line =>
      line._1.reverse.takeWhile(_.isWhitespace).length > 0
    }

  private[this] def deduceInput(args: Array[String]): scala.io.BufferedSource =
    if (args.length == 1) {
      io.Source.fromFile(args(0))
    }
    else if (args.length == 0) {
      io.Source.stdin
    }
    else {
      throw new IllegalArgumentException("Passed too many arguments.")
    }

  private[this] def exitOnHelpPlea(args: Array[String]): Unit =
    if (args.length == 1 && args(0) == "-h") {
      printHelpText()
      sys.exit(0)
    }
}