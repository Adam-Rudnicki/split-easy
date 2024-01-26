package com.mammuten.spliteasy.presentation.bill_details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TableHeader(
    memberHeaderText: String,
    amountPaidHeaderText: String,
    amountOwedHeaderText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            Text(text = memberHeaderText, modifier = Modifier.weight(0.7f))
            Text(text = amountPaidHeaderText, modifier = Modifier.weight(0.7f))
            Text(text = amountOwedHeaderText, modifier = Modifier.weight(1f))
        }
    )
}

@Preview
@Composable
fun TableHeaderPreview() {
    TableHeader(
        memberHeaderText = "Member",
        amountPaidHeaderText = "Amount paid",
        amountOwedHeaderText = "Amount owed"
    )
}