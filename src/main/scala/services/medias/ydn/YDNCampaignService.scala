package services.medias.ydn

import services.CampaignService
import services.core.ServiceResult

import scala.concurrent.Future

/**
 * Created by yavun9 on 2014/09/23.
 */
object YDNCampaignService extends CampaignService {

  def action(oldResults: Seq[ServiceResult]): Future[ServiceResult] = Future { ServiceResult(true) }

}
