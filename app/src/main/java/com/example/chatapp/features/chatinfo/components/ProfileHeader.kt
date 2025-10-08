package com.example.chatapp.features.chatinfo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.chatapp.data.local.entity.UserEntity
import com.example.chatapp.wds.theme.WdsTheme

@Composable
fun ProfileHeader(
    user: UserEntity?,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    // Profile section
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(WdsTheme.colors.colorSurfaceDefault)
            .padding(vertical = WdsTheme.dimensions.wdsSpacingTriple),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar - 120dp as per Figma
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        ) {
            if (user?.avatarUrl != null) {
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = "Profile picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(WdsTheme.colors.colorSurfaceHighlight),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user?.displayName?.firstOrNull()?.toString() ?: "?",
                        style = MaterialTheme.typography.headlineLarge,
                        color = WdsTheme.colors.colorContentDeemphasized
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(WdsTheme.dimensions.wdsSpacingDouble))

        // Name
        Text(
            text = user?.displayName ?: "Unknown",
            style = WdsTheme.typography.headline1,
            color = WdsTheme.colors.colorContentDefault
        )

        // Phone number
        user?.username?.let { phone ->
            Spacer(modifier = Modifier.height(WdsTheme.dimensions.wdsSpacingHalf))
            Text(
                text = phone,
                style = WdsTheme.typography.body1,
                color = WdsTheme.colors.colorContentDeemphasized
            )
        }
    }
}