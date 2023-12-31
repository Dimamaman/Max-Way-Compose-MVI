package uz.gita.dimaa.mymaxway.navigation

interface AppNavigator {
    suspend fun stackLog()
    suspend fun back()
    suspend fun backUntilRoot()
    suspend fun backAll()
    suspend fun navigateTo(screen: AppScreen)
    suspend fun replace(screen: AppScreen)
    suspend fun replaceAll(screen: AppScreen)
}