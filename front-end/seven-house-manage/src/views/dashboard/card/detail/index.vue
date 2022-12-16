<template>
    <div>
        <div class="card__header">
            <span class="header__title">{{title}}</span>
            <span class="header__count">{{count}}</span>
        </div>
        <div class="card__charts">
            <slot name="charts"></slot>
        </div>
        <div class="card__footer">
            <slot name="footer"></slot>
        </div>
    </div>
</template>

<script>
export default {
    name: "card-detail",
    props: ["title", "count"],
}
</script>

<style lang="scss" scoped>
.card__header {
    display: flex;
    align-items: center;
    color: #000000;
    height: 50px;

    // padding: 0 20px;
    .header__title {
        font-size: 12px;
    }

    .header__count {
        font-size: 18px;
        font-weight: 700;
        margin-left: 10px;
    }
}


.card__charts {
    height: 50px;
    font-size: 12px;
    color: #999;
    display: flex;
    align-items: center;
}

.card__footer {
    display: flex;
    align-items: center;
    height: 50px;
    border-top: 1px solid #f7f7f7;
    font-size: 12px;
    margin: 0 5px;
    // padding: 0 15px;
    box-sizing: border-box;
    color: #000000;
}
</style>