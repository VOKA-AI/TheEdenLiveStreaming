import serve from "@/utils/axios"

export const getFollowers = (userId:number)=>{
    return serve({
        url:'/friendship/get_followed_counts',
        method:'get',
        params:{
            userId
        }
    })
}

export const getRoomByType = (typeId:number,num:number,size:number)=>{
    return serve({
        url:'/live/get_room_by_type',
        method:'post',
        params:{
            typeId,
            num,
            size
        }
    })
}