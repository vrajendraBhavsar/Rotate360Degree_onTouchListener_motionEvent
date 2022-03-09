package com.example.product360view.domain.image

sealed class ImageType() {
    data class ResourceId(val imageRes: Int) : ImageType()  //R.drawable.ic_xyz
    data class Asset(val imageString: String) : ImageType()    //file:///asset/a/s/image.jpg
    data class BitMap(val imageString: String) : ImageType()
    data class BitMapRemoteImage(val imageUrl: String) : ImageType()
}