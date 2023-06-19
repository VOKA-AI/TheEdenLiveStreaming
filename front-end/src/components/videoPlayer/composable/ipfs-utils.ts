// 时间格式2023-02-10_14-57-03
function toDate(date_time:string) {
    const date = date_time.split("_")[0]
    const time = date_time.split("_")[1]
    const year = Number(date.split("-")[0])
    const month = Number(date.split("-")[1])
    const day = Number(date.split("-")[2])
    const hour = Number(time.split("-")[0])
    const minuts = Number(time.split("-")[1])
    const seconds = Number(time.split("-")[2])
    return new Date(year,month, day,hour, minuts, seconds)

}
// 时间格式2023-02-10_14-57-03
// 比较时间date_time1和date_time2
// if date_time1 > date_time2 return 1，也就是date_time1更晚
// if date_time1 = date_time2 return 0
// if date_time1 < date_time2 return -1
function compare_date(date_time1: string, date_time2:string) {
    const date1 = toDate(date_time1);
    const date2 = toDate(date_time2);
    if(date1 > date2) {
        return 1
    }
    if(date1 < date2) {
        return -1
    }
    return 0
}
// 文件格式2023-02-10_14-57-03.m3u8
export async function get_latest_m3u8_files(files:File[]) {
    let m3u8_files = await get_m3u8_files(files)
    let latest_filename = "1000-01-01_01-01-01.m3u8" // 哨兵
    let latest_date = latest_filename.split(".")[0]
    m3u8_files.forEach((file)=> {
        const date_in_filename = file.split(".")[0]
        if(compare_date(date_in_filename, latest_date) > 0) {
            latest_filename = file
            latest_date = date_in_filename
        }
    })
    return latest_filename;
}
export async function get_m3u8_files(files:File[]) {
    let res = []
    let file;
    for await (file of files) {
        if(file.name.split(".")[1] === "m3u8") {
            res.push(file.name);
        }
    }
    return res;
}