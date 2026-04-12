package com.deyvieat.smartstock.core.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.deyvieat.smartstock.core.notifications.NotificationHelper
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: InventoryRepository,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // Sincronizar con el backend
            repository.syncProducts()

            // Revisar stock bajo en Room y notificar
            val lowStock = repository.getLowStockList(threshold = 5)
            lowStock.forEach { product ->
                notificationHelper.showStockAlert(product.name, product.quantity)
            }

            Result.success()
        } catch (e: Exception) {
            // Si falla, WorkManager reintenta automáticamente
            Result.retry()
        }
    }
}