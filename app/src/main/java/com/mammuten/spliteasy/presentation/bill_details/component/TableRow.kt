package com.mammuten.spliteasy.presentation.bill_details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TableRow(
    memberName: String,
    amountPaid: String,
    amountOwed: String,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            Text(text = memberName, modifier = Modifier.weight(1f))
            Text(text = amountPaid, modifier = Modifier.weight(1f))
            Text(text = amountOwed, modifier = Modifier.weight(1f))
            IconButton(
                onClick = onDeleteClick,
                content = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete contribution"
                    )
                }
            )
        }
    )
}

@Preview
@Composable
fun TableRowPreview() {
    TableRow(
        memberName = "Member name",
        amountPaid = "Amount paid",
        amountOwed = "Amount owed",
        onDeleteClick = {}
    )
}