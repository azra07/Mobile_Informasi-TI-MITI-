package com.putrinadya.miti.domain.usecase.history

import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.utils.DummyData

class GetActivityHistoryUseCase {
    fun execute(): List<Event> {
        // Simulasi memuat riwayat kegiatan lampau (mengambil 3 event pertama dari DummyData)
        return DummyData.dummyEvents.take(3)
    }
}