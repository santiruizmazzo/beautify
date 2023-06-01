package beautify
import java.time.*
import beauty_categories.BeautyCategories

class BeautyService {

    String commercialName
    String description
    BeautyCategories category
    BigDecimal currentPrice
    List<DayOfWeek> offDays
    //TimeRange workingTimeRange
    //Duration duration

    static constraints = {
        commercialName blank: false, unique: true
        description blank: true
        currentPrice min: new BigDecimal(0)
        offDays size: 0..6
        //workingTimeRange blank: false, nullable: false
        //duration blank: false, nullable: false
    }
}
