/*
 * Copyright (c) 2020  STMicroelectronics – All rights reserved
 * The STMicroelectronics corporate logo is a trademark of STMicroelectronics
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * - Neither the name nor trademarks of STMicroelectronics International N.V. nor any other
 * STMicroelectronics company nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * - All of the icons, pictures, logos and other images that are provided with the source code
 * in a directory whose title begins with st_images may only be used for internal purposes and
 * shall not be redistributed to any third party or modified in any way.
 *
 * - Any redistributions in binary form shall not include the capability to display any of the
 * icons, pictures, logos and other images that are provided with the source code in a directory
 * whose title begins with st_images.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */

package com.st.BlueMS.demos.HighSpeedDatalog2.config

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.st.BlueSTSDK.Features.highSpeedDataLog.FeatureHSDataLogConfig
import com.st.BlueSTSDK.Features.highSpeedDataLog.communication.HSDSetDeviceAliasCmd
import com.st.BlueSTSDK.Manager
import com.st.BlueSTSDK.Node
import com.st.clab.stwin.gui.R

internal class HSD2BoardAliasDialogFragment : DialogFragment(){

    companion object{
        private val NODE_TAG_EXTRA = HSD2BoardAliasDialogFragment::class.java.name + ".NodeTag"
        fun newInstance(node: Node):DialogFragment{
            return HSD2BoardAliasDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(NODE_TAG_EXTRA,node.tag)
                }
            }
        }
    }

    private var mBoardName: TextView? = null

    private fun buildCustomView(): View {
        val layoutInflater = LayoutInflater.from(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_board_alias_conf,null)
        view?.apply {
            mBoardName = findViewById(R.id.aliasConf_alias_value)
        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.apply {
            setTitle(R.string.aliasConf_title)
            setView(buildCustomView())
            setPositiveButton(R.string.confDialog_save){ _, _ ->
                setBoardAlias()
            }
            setNegativeButton(R.string.confDialog_cancel){ dialog, _ ->
                dialog.dismiss()
            }
        }
        return dialogBuilder.create()
    }
    private val node: Node?
        get() = arguments?.getString(NODE_TAG_EXTRA)?.let {
            Manager.getSharedInstance().getNodeWithTag(it)
        }

    private fun setBoardAlias() {
        val configFeature = node?.getFeature(FeatureHSDataLogConfig::class.java)
        val name = mBoardName?.text ?: return
        configFeature?.sendSetCmd(HSDSetDeviceAliasCmd(name.toString()), Runnable { dismiss() })
    }
}