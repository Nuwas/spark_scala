package com.nuwas

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, expr, regexp_replace, split}

import java.io.FileNotFoundException

object ReadAndWriteCSV {

  def main(args : Array[String]): Unit ={

    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("ComNuwas")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")
    try {

      val filePath = args(0) // Input file path
      val outputFilePath = args(1) // output file path

      val moviesDf = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .csv(path = filePath)

      //This is the path if we are not taking it from args .csv(path = "data_folder/data.csv")
      //This is the actual path .csv("file:///Users/nuwasponnambathayil/IdeaProjects/spark_scala/data_folder/data.csv")

      println("*********DSL: Code Starts from here***********")
      val result = moviesDf.withColumn("New Title", expr("substring(title, 1, length (title)-6)"))
        .withColumn("New_Year", expr("substring(title, -6, 6)"))
        .withColumn("New_Year", regexp_replace (col ("New_year"), "[\\(, \\)]", ""))
        .withColumn("New_Genres",split(col("genres"),"\\|"))

        .select(col("*") +:(0 until 5).map(i =>col("New_Genres").getItem(i).as(s"col$i")):_*)
        .select ("movieid", "title", "genres", "New Title", "New_Year", "col0", "col1", "col2", "col3", "col4")


      //result.show(5, false)
      //result.write.format ("csv").save("file:///Users/nuwasponnambathayil/IdeaProjects/spark_scala/data_folder/movies_op/")

      result.printSchema ()
      result.show ( truncate = false)
      result.write.parquet ( path = outputFilePath)

      //result.write.parquet ( path = "data_folder/parquet")

    } catch {
      case exl: FileNotFoundException => println(s"File Not Found")
      case unknown: Exception => println(s"Not Sure why the error is coming")
    }

  }
}
