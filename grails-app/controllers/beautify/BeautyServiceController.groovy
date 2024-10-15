package beautify

class BeautyServiceController {

    static scaffold = BeautyService

    def list() {
        [beautyServices: BeautyService.list()]
    }

    def detail(Long id) {
        [beautyService: BeautyService.get(id)]
    }
}
