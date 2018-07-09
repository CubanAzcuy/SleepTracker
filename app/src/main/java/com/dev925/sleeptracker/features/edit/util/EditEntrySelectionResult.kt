package com.dev925.sleeptracker.features.edit.util

import com.dev925.sleeptracker.db.JournalState
import java.util.*

/**
 * Created by robertgross on 4/16/18.
 */
data class EditEntrySelectionResult(val state: JournalState, val date: Date?)