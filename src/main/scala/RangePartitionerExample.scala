import org.apache.log4j.{Level, Logger}
import org.apache.spark.{RangePartitioner, SparkContext}
import scala.util.Random

object RangePartitionerExample extends App {

  val sc = new SparkContext("local[*]", "RangePartitionerExample")
  Logger.getLogger("org").setLevel(Level.ERROR)

  val nums = (1 to 40)
  val rdd = sc.parallelize(nums).map((Random.nextInt(4), _))
  val rangePartitioner = new RangePartitioner(4, rdd)
  val partitioned = rdd.partitionBy(rangePartitioner)
  val pairsNumbers = partitioned.glom.map(_.length).collect

  println(pairsNumbers.mkString("\nLiczba par w kolejnych partycjach (1-4): (", ", ", ")"))
  println("\nPary w kolejnych partycjach (1-4):")

  partitioned.glom().collect().foreach(x => {x.foreach(print);println})

}