package carService.app.ui.main.menu_screens.personal_menu.request_services

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)
}
