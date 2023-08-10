package beautify

class BeautyServiceController {

    //static scaffold = BeautyService

    def index() {
        [beautyServices: BeautyService.list()]
    }

    def show(Long id) {
        [beautyService: BeautyService.get(id)]
    }
}
