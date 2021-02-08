- **Q1**：为什么要有stateRoot？

  **Answer**：在一个区块中，状态树是Merkle Patricia Tree，stateRoot字段是保存在区块头中的状态树的根哈希值，它方便节点间状态的互相验证，保证在交易的每个区块（每时每刻），所有节点的状态是一致的。

- **Q2**：nonce值有什么用？

  **Answer**：

  - 区块中的nonce值：与mixhash一起参与POW验证，决定挖矿的难度；
  - 交易中nonce值：交易发送者发送交易的计数，同一账户的交易会被依次确认，nonce值从0开始递增，用来确认交易顺序，防止双花，撤销pending中的交易，确定生成的合约地址。

- **Q3**：Hyperledger Fabric的特点，和Composer的关系？

  **Answer**：

  Hyperledger Fabric 是 Hyperledger 项目的一个组成部分，是一个区块链框架的实现，它具有以下特点：

  - 允许组件（如账本数据库，共识机制和成员服务）即插即用；
  - 利用容器技术，提供企业级网络安全性，可扩展性和机密性；
  - 智能合约在在 Fabric 中通过"Chaincode"得以实现。

  Hyperledger Composer 是基于 Hyperledger Fabric 构建区块链应用的应用开发框架，它让构建区块链应用程序变得更容易，它由三部分组成：一个名为 CTO 的建模语言；一个名为Hyperledger Composer Playground 的用户界面，用于快速配置、部署和测试业务网络；一个命令行接口 (CLI) 工具，用于将使用 Hyperledger Composer 建模的业务网络与一个正在运行的 Hyperledger Fabric 区块链业务网络实例相集成。

- **Q4**：联盟链智能合约和中心账本的区别？

  **Answer**：中心账本是记账权的集中，数据的集中，账本的记账权掌握在第三方机构手中，效率高、成本低，但其公正性、安全性无法保证；

  联盟链智能合约是中心账本和完全分布式账本的一个折中，属于“部分中心化”，它选取少数预选节点成为记账人，将记账人的数量调节在一个平衡安全性与效率的点上。

