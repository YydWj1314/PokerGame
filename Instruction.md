

## Knowlege Involved

### Back-ended 

- OOD Basic 面向对象基础
- Collections 集合 & Map映射 API
  - Map: HashMap, LinkedHashMap
  - Set: HashSet
  - List: ArrayList
- Stream  API 流的使用
  - 获取流: .stream()
  - 中间方法: .sort()、.filter()、.map() 等
  - 终结方法：toList()，toSet() 等
- Multi-thread 多线程
  - 多线程主要利用在实现多人同时游戏

### Fronted-end

- java GUI：Swing 

### JavaWeb 

- Client - Server 的基本架构
- 网络通信基本原理：TCP / UDP / IP
-  web API：socket，实现 Client - Server 之间的通信

## Rules Design

### General Rules:

- 类炸金花/德州扑克的 扑克牌点数比较游戏，
- 共3名玩家进行博弈，一共博弈三轮，每轮5回合，在每个回合中可以进行个性化策略选择，最终回合选择卡牌提交，按照牌型及特殊卡牌结算分数
- 最终轮后，结算总分及排名

### Poker Cards

1. 基本卡牌：4种花色，共52张
2. 道具卡牌（扩展玩法）：
   - 倍率卡：当前回合结算分数 \* 1.5
   - 偷牌卡：选择从其他玩家手牌偷一张牌
   - 变色卡：选择自己的一张卡牌，更改为制定花色
   - 改数卡：选择一张自己的卡牌，更改为指定数字
   - 看牌卡：查看选定玩家当前的全部手牌

### Basic rules

1. 共有三名玩家， 进行 3round（几轮合适？），5回合游戏（几回合合适？）
2. round 0：发3张底牌 + 看牌

- round 1 ～ 3：每回合每个玩家抽取1张基本卡 + 1 张道具卡 ，并选择个性化基本策略
  - 策略1：抽取1张基本牌
  - 策略2：发动道具卡效果（1回合只能发动一个）
  - 策略3；跳过
- round 4：每个玩家最后选择三张牌进行牌型大小比较
- 分数结算：
  - 该 round 玩家最终分数 = 最终牌面点数 \* 该轮玩家排名倍率 * (倍率卡)

### 牌型及分值

| Style                  | Example   | Rank | Comments               |
| ---------------------- | --------- | ---- | ---------------------- |
| 同花顺 straight flush  | A♠ K♠ Q♠  | 6    | 顺子，同花色           |
| 三条 three of one kind | 9♠ 9♥ 9♦  | 5    | 三张点数相同，花色不同 |
| 同花 flush             | K♠ 10♠ 7♠ | 4    | 三张点数不同，花色相同 |
| 顺子 straight          | K♠ Q♠ J♦  | 3    | 顺子，花色不同         |
| 对子 pair              | A♠ A♦ 10♠ | 2    | 两张相同               |
| 高牌 high card         | K♠ 10♠ 7♦ | 1    | 无组合，按最大牌比较   |
|                        |           |      |                        |

## Basic Object Design

游戏基本对象的设计：

### Card：卡牌

一套牌有4种花色，共计52张扑克牌

-  String suit  // 花色 {spades♠️, hearts♥️, diamonds♦️， clubs ♣️}
-  String rank  // 牌值 {A, 2, 3, …, 10, J, Q, K}
- Boolean inDeck

### Deck：52 张基本牌套牌

- List\<Card>

### Player： 玩家类

- String uid  // 玩家uid
- List\<Card> hand // 当前手牌
- handRank  // 当前手最高牌牌型 ? 这个属性貌似要用回溯算法列举出所有可能然后在进行比较，目前没实现，还需要探讨
- score // 玩家当前总分
- 玩家对象提供的方法：
  - getCardsFromDeckTop(Deck  deck, int n) 从牌堆顶获取n 张牌
  - selectCards(int n, Scanner scanner) 选择 n 张手牌组成一个List，scanner 用来控制台输入，后期要改
  - playCards(List\<Cards>)：打牌，玩家打出选中牌组成的List

## Programming Instructions

### Overall Plan

**构思了最基本的对象后，就开始设计游戏，我认为可以分为以下几个阶段：**

| Stage   | Task                         | Goal                                     | 预期时间 |
| ------- | ---------------------------- | ---------------------------------------- | -------- |
| Stage 1 | 后端：控制台实现简单游戏逻辑 | 控制台实现：洗、发、打、比牌             | 1 week   |
| Stage 2 | 后端：控制台实现复杂游戏逻辑 | 控制台实现：多轮游戏、分数结算、排名结算 | 1 week   |
| Stage 3 | 后端：控制台实现网络通信     | Client-Server + 多线程 + Web Socket      | 2 weeks  |
| Stage 4 | 前端：UI 搭建                | 实现前端图形化界面及交互                 | 2 weeks  |

### Stage1: Implementing Basic Logic in Console

这一个阶段的主要目的是实现的功能：控制台实现：初始化牌组、洗、发、打、比较牌

#### 初始化套牌

- 思考过程：
  1. Deck 中的成语变量List\<Card> cards 表示了deck中的全部扑克，但实例化Deck 后里面并没有扑克，因此Deck 需要提供InitDeck() 方法，将cards集合装入52 个扑克对象
- 需要完成的方法：deck.initDeck()

#### 洗牌、发牌、收牌

- 思考的过程：

  1. 功能如何实现？将两个步骤封装为对象的方法，调用方法实现
  2. 目前已经设计的三个基本对象{Card, Deck, Player}谁是实现这两个方法的对象？Deck，因为Deck 中初始化了全部的扑克牌
  3. 方法逻辑是什么

  - deck洗牌：直接调用collecions 中 shuffle() 实现随机打乱集合元素
  - deck发牌n张：发牌是从牌顶开始，那么取deck 中的Cards 前0～n 个元素的subList，直接remove 或 clear 
  - player 收牌：deck牌发出去了，那么player 也需要提供对应方法接受手牌

- 需要实现的方法
  - deck.shuffle()
  - deck.deal(int n)
  - Player.getCardsFromDeckTop(Deck deck, int n)

####  选牌、打牌

- KeyPoints：
  - 从哪里选牌？如何实现选牌？选的牌需要怎么处理？
  - 从哪里打牌？打牌是如何实现的？打出去的怎么处理?
- 方法：
  - player.selectCard()
  - player.playCards()

#### 比较牌❗️（重点 + 难点）

- **keyPoints**
  - 如何判断不同的牌型？
  - 需要从每个牌型获取什么信息？
  - 如何将不同的牌型实现大小比较？
- 实现的方法
  - 按需创建牌型类
  - HandEvaluator 工具类 
  - RankComparator 工具类 

##### 实现牌型对象：基类 Hand + 各个牌型的子类

- **基类Hand提供的方法，子类override：**
  1. 实现 compareble 接口，让牌型可以根据 Rate的value 比较（这里只能比较简单顺序，复杂的比较逻辑要重新定义comparator 类）
  2. 获取牌型名称：getHandName()
  3. 获取牌型等级：getHandValue()
  5. 获取组成牌型的对象列表：getMainCards()
     - 这里方法有个需要探究的问题，深拷贝 还是 浅拷贝？
     - 浅拷贝，返回的对象引用就是原对象
     - 深拷贝，返回的对象饮用是全新创建的
- **子类需要提供特殊方法：**
  1. StraightFlush 同花顺类：
     - 获取当前花色：getStraightFlushSuit()
     - 获取最高牌对象：getHighestCard()
  2. ThreeOfOoneKind 三条类：
     - 获取三条花色：getThreeOfOneKindSuit()
     - 获取三条点数：getThreeOfOneKindRank()
  3. Flush 同花类：
     - 获取同花花色：getFlushSuit()
  4. Straight 顺子类：
     - 获取最高牌对象：getHighestCard()
  
  5. Pair 对子类：
     - 获取对子点数：getPairRank()
     - 获取非对子卡牌对象：getNonPairCard()
  6. HighCard 高牌类
     - 无子类特殊方法

##### 实现牌型类型识别：牌型判断工具类 -- HandEvaluator

- Evaluator 作为牌型判断的工具类，定义方法：
  - 对手牌进行排序方法：sortCards()
  - 获取当前牌型：getResult()
  - 判断同花顺方法： isStraightFlush() 
  - 判断三条方法：isThreeOfOneKind()
  - 判断同花方法：isFlush()
  - 判断顺子方法：isStraight()
  - 判断对子方法：isPair()
  - 判断高牌方法：isHighCard()

##### 实现牌型大小比较及排序：比较器 -- RankComparator

- 这里就是比较算法的实现类，我们需要让牌型可以进行比较 + 排序，那么重点在于实现 Comparator 接口中的 compare(o1, o2) 方法。
- 通过自定义 RankComparator 类并 implements Comparator，重写compare 方法，实现自定义比较逻辑
- 这里就需要具体考虑不同牌型之间如何比较、相同牌型如何进行比较

```java
public abstract class T implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        // 具体比较逻辑 ...
    }
}
/*
*compare(o1, o2) 方法返回值：
    o1 = o2 --> return 0
    o1 > o2 --> return 1
    o1 <o2 --> return -1
*/
```

#### GameController 实现

所有简单的基本方法已经实现，GameController 控制整个游戏的流程，游戏的流程应该分为以下几个阶段：

- ```
  * 初始化：3*player + 1*deck
  * 洗牌
  * 发牌
  * 选牌
  * 打牌
  * map 收集
  * 确定牌型
  ```

  

  









![image-20250303095520161](/Users/yueyangdong/Library/Application Support/typora-user-images/image-20250303095520161.png)

![image-20250303100300736](/Users/yueyangdong/Library/Application Support/typora-user-images/image-20250303100300736.png)















