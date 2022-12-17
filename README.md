## Introduction

***The Eden*** is a decentralized live streaming platform for Web3.0. 
which focusing on decentralized games and AMA interviews on [ETH](https://ethereum.org/en/) and Polygon network. 

The project will provide 3D NFT avatar generating technology from 2D PFP NFT by using [our AI algorithm](https://github.com/VOKA-AI/3DAvatarGenerator), and be able to live stream with the 3D avatars. 
Different from previous live broadcasts, The Eden creates the opportunity for interaction between viewers and anchors in the same space through Metaverse space scenes. 
GameFi providers and AMA KOLs can open their own DAO to promo their own channel within the platform.

<div align="center">
  <img src=https://github.com/VOKA-AI/.github/blob/main/assets/architecture.jpg width=80% align="center"/>
  <p>Architecture</p>
</div>

 

Media data delivery of the platform was based on [IPFS](https://ipfs.tech/). The process is as follows:
<div align="center">
  <img src=https://github.com/VOKA-AI/.github/blob/main/assets/process.jpg width=80% align="center"/>
  <p>process</p>
</div>

1. KOLs makes videos on the client, including fetching NFT picture from blockchain, generate 3D avatar, and used as face mask, then send them to ***The Eden*** server over [socket.io](https://github.com/socketio/socket.io).
2. ***The Eden*** server will do some pre-processing, such as encoding, then uploading it to IPFS network.
3. Audiences using client to interact with ***The Eden***, geting data hash from server, combine with *smart contract*, audiences can pay to subscribe, send virtual gifts to KOLs.
4. Audiences use data hash to get live stream from IPFS.

Our servers run both IPFS and our service, responsible for uploading data quickly to IPFS, reponsing to the request of data hash from subscribers, as well as identifying authentication.

<div align="center">
  <img src=https://github.com/VOKA-AI/.github/blob/main/assets/network.jpg width=80% align="center"/>
  <p>Media delivery network</p>
</div>

The advantages of IPFS based media delivery system are as follows:
1. Suitable to Web3.0, has the advantages of Web3.0.
2. Higher availability and scalibility.
3. Save bandwidth cost.
4. Cannot be controlled by one authority or government.
5. More secure.


As mentioned above, ***The Eden*** provide 3D avatar generation services, 
which can generate 3D models from 2D NFT pictures. 

Our 3D avatar generating algorithm was based on styleGAN

<div align="center">
  <img src=https://github.com/VOKA-AI/.github/blob/main/assets/3DAvatarGenerating.jpeg width=80% align="center"/>
  <p>Avatar Generating AI</p>
</div>

https://user-images.githubusercontent.com/19359257/206367275-0687a023-691e-4ab9-90af-7f9d454fad4f.mp4

The generated avatar can be used as secondary creation NFT, and can also be used in live streaming as [AR mask](https://github.com/VOKA-AI/react-face-mask).

<div align="center">
  <img src=https://github.com/VOKA-AI/.github/blob/main/assets/ARFaceMask.jpg width=80% align="center"/>
  <p>AR Face Mask Process</p>
</div>


<div align="center">
  <img src=https://github.com/VOKA-AI/.github/blob/main/assets/ARMask1.png width="300"/><img src=https://github.com/VOKA-AI/.github/blob/main/assets/ARMask2.png width="300"/>
</div>


https://user-images.githubusercontent.com/19359257/206367690-ee1ba05c-1a6b-4a89-84a2-1aa091141a09.mov


Lack of usage scenarios is a bottleneck that restricts the development of NFT.
Using NFT to generate avatar, used in live streaming and other fields like video chat with friends can promote the development of NFT.

As a Web3.0 platform, ***The Eden*** will issue its own tokens.


## Business Model & Token Related

### Business Model

<div align="center">
  <img src=https://user-images.githubusercontent.com/20713531/206183113-14125208-6b75-44a5-9c35-2ad1a97f7cbb.jpg width=80% align="center"/>
  <p>Business Model</p>
</div>

