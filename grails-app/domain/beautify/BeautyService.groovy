package beautify
import java.time.*
import beauty_categories.*

class BeautyService {

    String commercialName
    String description
    BeautyCategories category
    BigDecimal currentPrice
    List<DayOfWeek> offDays
    TimeRange workingHours
    Duration duration

    static constraints = {
        commercialName blank: false, unique: true
        description blank: true
        currentPrice min: new BigDecimal(0)
        offDays size: 0..6
    }
}
