package models

import org.joda.time.DateTime

/**
 * Created by yavun9 on 2014/09/23.
 */

/**
 * Model class of Campaign
 */
case class Campaign(id: Long, name: String, status: Status, startDate: Option[DateTime], endDate: Option[DateTime])

/**
 * Search Campaign
 */
case class CampaignSelector(ids: Seq[Long] = Nil, status: Option[Status] = None)

