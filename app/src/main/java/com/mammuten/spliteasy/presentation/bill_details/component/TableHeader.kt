package com.mammuten.spliteasy.presentation.bill_details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TableHeader(
    memberHeaderText: String,
    amountPaidHeaderText: String,
    amountOwedHeaderText: String,
    onCalculateClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(text = memberHeaderText, modifier = Modifier.weight(1f))
            Text(text = amountPaidHeaderText, modifier = Modifier.weight(1f))
            Text(text = amountOwedHeaderText, modifier = Modifier.weight(1f))
            IconButton(
                onClick = onCalculateClick,
                content = {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = "Calculate"
                    )
                }
            )
        }
    )
}

@Preview
@Composable
fun TableHeaderPreview() {
    TableHeader(
        memberHeaderText = "Member",
        amountPaidHeaderText = "Amount paid",
        amountOwedHeaderText = "Amount owed",
        onCalculateClick = {}
    )
}