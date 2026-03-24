### UI.wz

1. MapLogin.img.xml

> 从一个地图到另一个地图的时候所载入的事件，比如我们从出租车那里去其他地图

* info

| 名称          | 解释                     | 值 |
|-------------|------------------------|---|
| version     | 版本号                    |   |
| streetName  | 街道名                    |   |
| mapName     | 地图名称                   |   |
| mapDesc     | 地图描述                   |   |
| bgm         | 背景音乐                   |   |
| town        | 村庄，是否为回城卷轴目标?          |   |
| forceReturn | 强制返回                   |   |
| mobRate     | 聚集率                    |   |
| mapMark     |                        |   |
| hideMiniMap | 隐藏地图，也是用数字 来表示具体是哪个地图。 |   |

* back

* 0~7的数字

* reactor

* foothold

* ladderrope

* minimap

* portal

### Skill.wz

> 攻击类技能

| 名称            | 解释                                                | 值 |
|---------------|---------------------------------------------------|---|
| hs            | 技能简介级别号 与String.wz相连                              |   |
| req           | 要求其它技能级别达到x时才可加此技能                                |   |
| masterLevel   | 技能初始级别上限 4转技能大部分为10 需要用技能书提升                      |   |
| mob           | 对怪物的影响效果(头顶上- -)                                  |   |
| skillType     | 技能类型 被动1 主动辅助 2 主动攻击式辅助 3                         |   |
| disable       | 值为1时禁止提升技能级别                                      |   |
| invisible     | 技能是否可见 值为0或不存在此项时可见                               |   |
| elemAttr      | 攻击属性 f为火 其它暂且不在此添加                                |   |
| action        | 技能发动时执行的动作 延迟由此产生                                 |   |
| prop          | 发动几率 有多种类型 1 被动技能发动几率(如终极箭)   2 技能特殊效果发动几率(爆炸箭昏迷) |   |
| mastery       | 武器熟练度 游戏中的%是按照该值*5 如60%熟练对应的是12                   |   |
| mobCount      | 技能攻击数量                                            |   |
| bulletCount   | 技能攻击次数                                            |   |
| cooltime      | 冷冻时间 技能连续使用的时间间隔                                  |   |
| damage        | 伤害% 按照角色的攻击值                                      |   |
| range         | 攻击长度 本处为单向攻击长度                                    |   |
| hpCon         | HP消耗                                              |   |
| mpCon         | MP消耗                                              |   |
| lt.X          | 技能影响范围 右上X                                        |   |
| lt.Y          | 技能影响范围 右上Y                                        |   |
| rb.X          | 技能影响范围 左下X                                        |   |
| rb.Y          | 技能影响范围 右下Y                                        |   |
| iconMouseOver | 图象效果                                              |   |
| iconDisabled  | 图象效果                                              |   |
| icon          | 图象效果                                              |   |
| effect        | 图象效果                                              |   |

### Mob.wz

> 怪物类

| 名称         | 解释                     | 值 |
|------------|------------------------|---|
| acc        | 怪物攻击的命中值               |   |
| bodyAttack | 身体碰撞（改0可做到碰怪无敌）        |   |
| eva        | 回避值                    |   |
| exp        | 击杀后获得的经验值              |   |
| fs         | 不明，常值为10               |   |
| level      | 怪物级别                   |   |
| maxHP      | 怪物最大血值                 |   |
| maxMP      | 怪物最大魔值                 |   |
| MADamage   | 魔法攻击                   |   |
| MDDamage   | 魔法防御                   |   |
| PADamage   | 物理攻击                   |   |
| PDDamage   | 物理防御                   |   |
| speed      | 移动速度                   |   |
| undead     | 是否为不死系（决定牧师是否可治疗并造成伤害） |   |

> 怪物攻击部分(attack1....4)

| 名称          | 解释               | 值 |
|-------------|------------------|---|
| attackAfter | 攻击效果影响时间         |   |
| conMP       | 耗费的魔量            |   |
| knockback   | 击退角色             |   |
| range       | 攻击影响范围           |   |
| tremble     | 屏幕震动（1：震动，0：不震动） |   |
| type        | 攻击类型             |   |

### Character.wz

1.Accessory

> 脸饰

2.Longcoat

> 套服

3.Rind

> 戒指

4.Pants

> 下身

5.Glove

> 手套

6.Shield

> 盾牌

7.TamingMob

> 骑兽

| 名称              | 解释               | 值 |
|-----------------|------------------|---|
| afterImage      | 攻击的划痕            |   |
| attack          | 此处意义不明           |   |
| attackSpeed     | 攻击速度             |   |
| cash            | 是否现金道具（0：不是，1：是） |   |
| incACC          | 增加命中             |   |
| incDEX          | 增加敏捷             |   |
| incINT          | 增加智力             |   |
| incLUK          | 增加运气             |   |
| incSTR          | 增加力量             |   |
| incPAD          | 增加物理攻击           |   |
| incHP           | 增加血值             |   |
| incMP           | 增加魔值             |   |
| incMAD          | 增加魔法攻击           |   |
| incMDD          | 增加魔法防御           |   |
| incPDD          | 增加物理防御           |   |
| reqDEX          | 装备所要求的敏捷         |   |
| reqINT          | 装备要求的智力          |   |
| reqJob          | 装备要求的职业          |   |
| reqLevel        | 装备要求的级别          |   |
| reqLUK          | 装备要求的幸运          |   |
| reqSTR          | 装备要求的力量          |   |
| tuc             | 可升级次数            |   |
| knockback       | 击退怪物几率           |   |
| notSale         | 无法出售             |   |
| only            | 固有道具             |   |
| price           | 出售价格 = 价格的二分之一   |   |
| timeLimited     | 时间限制             |   |
| tradeBlock      | 交易限制             |   |
| equipTradeBlock | 装备后交易限制          |   |
| exp             | 武器升级所需武器经验值      |   |
| incPADMin       | 升级时武器增加的最小物理攻击   |   |
| incPADMax       | 升级时武器增加的最大物理攻击   |   |
| incMADMin       | 升级时武器增加的最小魔法攻击   |   |
| incMADMax       | 升级时武器增加的最大魔法攻击   |   |
| incSTRMin       | 升级时武器增加的最小力量     |   |
| incSTRMax       | 升级时武器增加的最大力量     |   |
| incDEXMin       | 升级时武器增加的最小敏捷     |   |
| incDEXMax       | 升级时武器增加的最大敏捷     |   |
| incINTMin       | 升级时武器增加的最小智力     |   |
| incINTMax       | 升级时武器增加的最大智力     |   |
| incLUKMin       | 升级时武器增加的最小运气     |   |
| incLUKMax       | 升级时武器增加的最大运气     |   |

### Item.wz

### Etc.wz

1.BlockReason.img

> 封号原因

| 名称   | 解释   | 值 |
|------|------|---|
| type | 类型   |   |
| msg  | 详情原因 |   |

2.ChatBlockReason.img

> 聊天禁止原因

| 名称   | 解释   | 值 |
|------|------|---|
| type | 类型   |   |
| msg  | 详情原因 |   |

3.ForbiddenName.img

> 名字禁用目录




### TamingMob.wz

| 名称      | 解释                 | 值 |
|---------|--------------------|---|
| fatigue | 疲劳一定时间（估计1分钟）内增加的值 |   |
| jump    | 骑上骑宠后角色的跳跃值        |   |
| speed   | 骑上骑宠后角色的移动速度       |   |
| swim    | 骑上骑宠后角色的游泳速度       |   |
| fs      |                    |   |

### Morph.wz

> 变身

| 名称     | 解释         | 值 |
|--------|------------|---|
| jump   | 变身后角色的跳跃力  |   |
| speed  | 变身后角色的移动速度 |   |
| swim   | 变身后角色的游泳速度 |   |
| fs     |            |   |
| jump   | 形态         |   |
| stand  | 形态         |   |
| walk   | 形态         |   |
| prone  | 形态         |   |
| ladder | 形态         |   |
| rope   | 形态         |   |
| fly    | 形态         |   |

### Quest.wz

> 任务

| 名称              | 解释                            | 值 |
|-----------------|-------------------------------|---|
| nextQuest       | 下一个任务（任务链中的后续任务）              |   |
| exp             | 任务完成奖励的经验值                    |   |
| money           | 任务完成奖励的金钱                     |   |
| npc             | 任务所要找的NPC（非玩家角色）              |   |
| job             | 任务所要求的职业                      |   |
| count           | 任务所要求的物品数量                    |   |
| id              | 任务所要求的物品ID                    |   |
| end             | 任务结束时间（如：2026-03-21 23:59:59） |   |
| start           | 任务开始时间                        |   |
| interval        | 重复接到该任务的间隔时间（单位：秒/分钟）         |   |
| normalAutoStart | 任务是否自动开始（0：否，1：是）             |   |

### Map.wz

> 地图部分

| 名称           | 解释                              | 值 |
|--------------|---------------------------------|---|
| bgm          | 地图背景音乐                          |   |
| cloud        | 是否启用云层效果（0：否，1：是）               |   |
| fieldLimit   | 地图限制类型（如：区域限制、战斗限制等）            |   |
| forcedReturn | 强制返回（值为999999999时，通常返回附近主城或本地图） |   |
| hideMinimap  | 是否隐藏迷你地图                        |   |
| mapMark      | 地图标记（用于标识重要地点，如任务点、商店）          |   |
| mobRate      | 怪物刷新比例（影响怪物密度）                  |   |
| returnMap    | 返回地图ID（角色死亡或传送后返回的地图）           |   |
| town         | 是否为村庄/主城（0：否，1：是）               |   |
| version      | 版本号（可能用于版本控制或更新标记）              |   |
| VRBottom     | 地图底部坐标（虚拟坐标系）                   |   |
| VRLeft       | 地图左边坐标（虚拟坐标系）                   |   |
| VRRight      | 地图右边坐标（虚拟坐标系）                   |   |
| VRTop        | 地图顶边坐标（虚拟坐标系）                   |   |

> 地板部分

| 名称      | 解释     | 值 |
|---------|--------|---|
| next    | 下一个地板  |   |
| prev    | 上一个地板  |   |
| x1      | 地板的左边X |   |
| x2      | 地板的右边X |   |
| y1      | 地板的上边y |   |
| y2      | 地板的下边y |   |
| ToolTip | 提示的文本  |   |
