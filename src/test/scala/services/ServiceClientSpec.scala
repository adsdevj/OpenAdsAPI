package services

import org.scalatest._
import services.core.{Result, ServiceExceptionResult, ServiceResult, ServiceClient}

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

/**
 * Created by yabumoto on 2014/09/21.
 */
class ServiceClientSpec extends FlatSpec with Matchers with ParallelTestExecution {

  object DummyClient extends ServiceClient

  "execute action" should "can get result" in {
    val action = DummyClient action { () =>
      Future {
        new ServiceResult(Result.SUCCESS)
      }
    }
    val result = Await.result(action, Duration.Inf)
    result.code should equal (Result.SUCCESS)
  }

  "execute error action" should "can get exception" in {
    val action = DummyClient action { () =>
      Future {
        2 / 0
        new ServiceResult(Result.SUCCESS)
      }
    }
    val result = Await.result(action, Duration.Inf)
    result.code should equal (Result.ERROR)
    result.getClass() should equal (classOf[ServiceExceptionResult])
    val exceptionResult = result.asInstanceOf[ServiceExceptionResult]
    exceptionResult.exception.getClass() should equal (classOf[java.lang.ArithmeticException])
  }

  "if execution failure and can retry" should "retry and can get all result" in {
    val action = DummyClient action { () =>
      Future {
        new ServiceExceptionResult(true, new Exception("error test."))
      }
    }
    val result = Await.result(action, Duration.Inf)
    result.code should equal (Result.ERROR | Result.RETRY)
    result.getClass() should equal (classOf[ServiceExceptionResult])
    val errors = result.errors
    errors.length should equal (5)
    errors.foreach { error =>
      error.getClass() should equal (classOf[ServiceExceptionResult])
    }
  }

}
