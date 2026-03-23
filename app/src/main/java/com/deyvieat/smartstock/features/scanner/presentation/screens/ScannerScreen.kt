package com.deyvieat.smartstock.features.scanner.presentation.screens

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.scanner.presentation.components.ScannedProductCard
import com.deyvieat.smartstock.features.scanner.presentation.viewmodels.ScannerViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScannerScreen(onBack: () -> Unit,
                  onProductClick: (Product) -> Unit,
                  viewModel: ScannerViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermission.status.isGranted) cameraPermission.launchPermissionRequest()
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Escanear Producto") },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Atrás") } })
    }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (cameraPermission.status.isGranted) {

                // HARDWARE 1: Vista de la cámara trasera con CameraX
                CameraPreview(modifier = Modifier.fillMaxWidth().height(300.dp),
                    isTorchOn = uiState.isTorchOn,
                    onBarcodeDetected = { viewModel.onBarcodeDetected(it) })

                Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // HARDWARE 2: Botón de Linterna
                    Button(onClick = { viewModel.toggleTorch() }, modifier = Modifier.weight(1f)) {
                        Text(if (uiState.isTorchOn) "🔦 Linterna ON" else "🔦 Linterna OFF")
                    }
                    if (uiState.scannedCode.isNotEmpty()) {
                        OutlinedButton(onClick = { viewModel.clearScan() }) {
                            Icon(Icons.Default.Close, null)
                        }
                    }
                }

                if (uiState.scannedCode.isNotEmpty()) {
                    Text("QR leído: ${uiState.scannedCode}",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary)
                }

                when {
                    uiState.isProcessing -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp))
                    uiState.error != null -> Text(uiState.error!!,
                        modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.error)
                    else -> LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp)) {
                        items(uiState.foundProducts) { product -> ScannedProductCard(product,
                            onClick = {onProductClick(product)}) }
                    }
                }
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Se necesita permiso de cámara")
                        Button(onClick = { cameraPermission.launchPermissionRequest() }) {
                            Text("Conceder permiso")
                        }
                    }
                }
            }
        }
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@Composable
fun CameraPreview(modifier: Modifier = Modifier, isTorchOn: Boolean, onBarcodeDetected: (String) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val executor = remember { Executors.newSingleThreadExecutor() }
    var camera by remember { mutableStateOf<androidx.camera.core.Camera?>(null) }

    LaunchedEffect(isTorchOn) { camera?.cameraControl?.enableTorch(isTorchOn) }

    AndroidView(modifier = modifier, factory = { ctx ->
        val previewView = PreviewView(ctx)
        ProcessCameraProvider.getInstance(ctx).addListener({
            val provider = ProcessCameraProvider.getInstance(ctx).get()
            val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
            val analyzer = ImageAnalysis.Builder()
                .setTargetResolution(Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build().also { analysis ->
                    analysis.setAnalyzer(executor) { imageProxy ->
                        val img = imageProxy.image
                        if (img != null) {
                            BarcodeScanning.getClient()
                                .process(InputImage.fromMediaImage(img, imageProxy.imageInfo.rotationDegrees))
                                .addOnSuccessListener { barcodes ->
                                    barcodes.firstOrNull()?.rawValue?.let { onBarcodeDetected(it) }
                                }
                                .addOnCompleteListener { imageProxy.close() }
                        } else imageProxy.close()
                    }
                }
            provider.unbindAll()
            camera = provider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, analyzer)
        }, ContextCompat.getMainExecutor(ctx))
        previewView
    })
}
