package com.riyas.topstories.model

import android.os.Parcel
import android.os.Parcelable

class StoryResponse() : BaseApiResponse(),Parcelable{
    var by:String?=null
    var descendants:String?=null
    var id:String?=null
    var title:String?=null
    var type:String?=null
    var url:String?=null
    var kids:List<String>?=null
    var score:Int=0
    var time:Long=0

    constructor(parcel: Parcel) : this() {
        by = parcel.readString()
        descendants = parcel.readString()
        id = parcel.readString()
        title = parcel.readString()
        type = parcel.readString()
        url = parcel.readString()
        kids = parcel.createStringArrayList()
        score = parcel.readInt()
        time = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(by)
        parcel.writeString(descendants)
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeString(url)
        parcel.writeStringList(kids)
        parcel.writeInt(score)
        parcel.writeLong(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoryResponse> {
        override fun createFromParcel(parcel: Parcel): StoryResponse {
            return StoryResponse(parcel)
        }

        override fun newArray(size: Int): Array<StoryResponse?> {
            return arrayOfNulls(size)
        }
    }


}