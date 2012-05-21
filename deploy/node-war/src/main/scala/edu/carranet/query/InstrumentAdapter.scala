package edu.carranet.query
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.ProceedingJoinPoint
import net.shrine.util.Loggable
import com.yammer.metrics.reporting.GraphiteReporter
import java.util.concurrent.TimeUnit
import com.yammer.metrics.Instrumented


/**
 * @author Justin Quan
 * @date 9/22/11
 * @link http://chip.org
 *       <p/>
 *       NOTICE: This software comes with NO guarantees whatsoever and is
 *       licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */
@Aspect
class InstrumentAdapter(
    val graphiteHost: String) extends Loggable with Instrumented {
  val performTimer = metrics.timer("adapter.perform")
  GraphiteReporter.enable(1, TimeUnit.MINUTES, graphiteHost, 2003)

  @Around("execution(* net.shrine.adapter.Adapter.perform(..))")
  def profileAdapter(pjp: ProceedingJoinPoint): java.lang.Object = {
    try {
      performTimer.time{
        pjp.proceed()
      }
    }
  }
}