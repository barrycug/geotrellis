package geotrellis.spark.io.s3

import org.scalatest._
import scala.io.Source
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import scala.collection.JavaConverters._

class MockS3ClientSpec extends FunSpec
  with Matchers
{
  describe("MockS3Clinet") {
    import S3Client._

    val client = new MockS3Client
    it("should write a key") {
      client.putObject("bucket", "firstKey", "data".getBytes("UTF-8"))
    }

    it("should read a key") {
      val obj = client.getObject("bucket", "firstKey")
      val str = Source.fromInputStream(obj.getObjectContent).mkString      
      str should be ("data")
    }

    it("should list a key") {
      val listing = client.listObjects("bucket", "")
      listing.getObjectSummaries.asScala.map(_.getKey) should contain ("firstKey")
    }

    it("should list with a prefix") {
      client.putObject("bucket", "sub/key1", "data1".getBytes("UTF-8"))
      client.putObject("bucket", "sub/key2", "data2".getBytes("UTF-8"))
      val listing = client.listObjects("bucket", "sub")
      listing.getObjectSummaries.asScala.map(_.getKey) should contain allOf ("sub/key1", "sub/key2")
    }
  }
}