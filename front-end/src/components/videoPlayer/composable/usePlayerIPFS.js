import Hls from 'hls.js'
import {create} from 'ipfs-http-client';
import { useLiveStore } from "@/stores/live";
import { HlsjsIPFSLoader } from './hls-ipfs-loader';
import { get_latest_m3u8_files } from './ipfs-utils';

let NODE_HOST = "35.77.2.79";
let NODE_PORT = 5001;
let IPNS = "/ipns/k51qzi5uqu5dlx3o6263fj29q7q0rnmzy4o4q30hibchdtd5u6myoj0hshmvaz/";

let LIVE_STREAM_IPFS_DIR = "/test/" // 目前需要以/结尾，否则会播放失败
let USE_IPNS = false;

// 修改这里，增加对IPFS HLS直播的适配。
const liveStore = useLiveStore();

const getIPFSClient = async () => {
  //console.log(liveStore.roomInfo.ipfshost)
  //console.log(liveStore.roomInfo.ipfsport)
  //console.log(liveStore.roomInfo.ipnsaddress)
  const client = await create({host: liveStore.roomInfo.ipfshost, port:liveStore.roomInfo.ipfsport, protocol: 'http'});
  return client;
};

export const loadHLS = async () => {
  const client = await getIPFSClient();
  const ipns_addr = liveStore.roomInfo.ipnsaddress;
  Hls.DefaultConfig.loader = HlsjsIPFSLoader
  Hls.DefaultConfig.debug = false
  if (Hls.isSupported()) {
    const video = document.getElementById('video')
    const status = document.getElementById('status')
    //video.load()
    const hls = new Hls()
    hls.config.maxMaxBufferLength = 120;
    hls.config.ipfs = client;
    hls.config.ipnsAddr = ipns_addr;
let hash;
    hls.config.dirName = LIVE_STREAM_IPFS_DIR
    hls.config.useIPNS = USE_IPNS;
    let files;
    if(USE_IPNS) {
      let hash;
      hash = (await (await client.name.resolve(ipns_addr).next())).value.split("/")[2];
      files = await client.ls(hash);
    } else {
      files = await client.files.ls(LIVE_STREAM_IPFS_DIR);
    }

    const latest_m3u8_filename = await get_latest_m3u8_files(files);
    hls.on(Hls.Events.ERROR, function(event, data) {
      //console.log(event);
      //console.log(data);
    })

    hls.on(Hls.Events.MEDIA_ATTACHED, function() {
      //console.log("video and hls.js are now bound together!")
    })

    hls.on(Hls.Events.MANIFEST_PARSED, function(event, data) {
      //const node = document.createTextNode("Video ready...");
      //status.appendChild(node);
      video.play()
    });
    hls.loadSource(latest_m3u8_filename)
    hls.attachMedia(video)
  }

}
