package services.medias.gdn

import services.CampaignService
import services.core.ServiceResult

import scala.concurrent._

/**
 * Created by yavun9 on 2014/09/23.
 */
object GDNCampaignService extends CampaignService {

  def action(oldResults: Seq[ServiceResult]): Future[ServiceResult] = Future { ServiceResult(true) }

}
