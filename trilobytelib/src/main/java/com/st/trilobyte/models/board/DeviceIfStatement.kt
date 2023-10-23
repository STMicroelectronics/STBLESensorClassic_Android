package com.st.trilobyte.models.board

import android.util.Log
import com.google.gson.GsonBuilder
import com.st.trilobyte.models.Flow
import com.st.trilobyte.models.Sensor
import com.st.trilobyte.models.board.serializer.BoardFlowSerializer
import com.st.trilobyte.models.board.serializer.BoardSensorSerializer
import java.io.Serializable

data class DeviceIfStatement private constructor(val expression: DeviceFlow, val statements: List<DeviceFlow>) : Serializable {

    companion object {

        fun getBoardStream(exp: Flow, stats: List<Flow>): String {

            Log.i("FlowTmp", "Flow exp =$exp")

            val expression = DeviceFlow.transform(exp)

            Log.i("FlowTmp", "Flow expression =$expression")

            Log.i("FlowTmp", "Flow stats =$stats")

            val statements = mutableListOf<DeviceFlow>()
            stats.forEach { statements.add(DeviceFlow.transform(it)) }

            Log.i("FlowTmp", "Flow statements =$statements")
            val retValue = GsonBuilder()
                    .registerTypeAdapter(Flow::class.java, BoardFlowSerializer)
                    .registerTypeAdapter(Sensor::class.java, BoardSensorSerializer)
                    .create()
                    .toJson(DeviceIfStatement(expression, statements))

            Log.i("FlowTmp", "Flow getBoardStream =$retValue")

            return retValue
        }
    }
}