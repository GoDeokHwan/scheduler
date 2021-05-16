<template>
  <v-app style="overflow: auto">
    <div style="height: 100%; display: inline-block; width: 60%;">
      <v-sheet height="800" color="white"
               elevation="7"
               width="810"
               style="top: 50px;
               position: relative;
               left: 2%;"
      >
        <h1 style="align-content: center">일정관리</h1>
        <span>선택 : {{selectYear}} - {{selectMonth}} - {{selectDate}}</span>
        <div style="margin: auto;">
          <table style="margin: auto">
            <thead>
            <tr>
              <td v-for="str in week">{{str}}</td>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in totalWeek">
              <template v-for="(itemDay, indexDay) in dayArr">
                <td v-if="indexDay < (index + 1) * 7 && indexDay >= index * 7" v-on:click="selectDay(itemDay)">
                  <div>
                    <span class="scheduler-day" v-bind:class="{active: isSelect(itemDay), holiday: itemDay.isHoliday, holidaySaturday: itemDay.isHolidaySaturday}">{{itemDay.date}}</span>
                  </div>
                </td>
              </template>
            </tr>
            </tbody>
          </table>
        </div>
      </v-sheet>
    </div>
    <div style="display: inline-block; position: absolute; width: 45%; right: 0;">
      <v-sheet height="300" color="white"
               elevation="7"
               style="top: 50px;
               position: relative;
               margin-right: 20px">
      <h1>일정리스트</h1>
      </v-sheet>
      <v-sheet height="486" color="white"
               elevation="7"
               style="top: 50px;
               position: relative;
               margin-top: 2%;
               margin-right: 20px">
      <h1>일정 등록/수정</h1>
      </v-sheet>
    </div>
  </v-app>
</template>

<script>
import helpers from '../../util/Helpers'

export default {
  name: 'Scheduler',
  metaInfo: {
    title: '일정관리'
  },
  data () {
    return {
      week: ['일','월','화','수','목','금','토'],
      dayArr: [],
      selectYear: '',
      selectMonth: '',
      selectDate: '',
      totalWeek: 0
    }
  },
  created () {
    let date = new Date()
    this.selectYear = date.getFullYear()
    this.selectMonth = helpers.fillStr(2, String(date.getMonth() + 1), '0')
    this.selectDate = helpers.fillStr(2, String(date.getDate()), '0')
    this.setDayArr(date.getFullYear(), date.getMonth() + 1)
  },
  methods: {
    setDayArr (inpYear, inpMonth) {
      this.dayArr = []
      // 이전 달
      let thisMonth = new Date(inpYear, inpMonth - 1, 1)
      if (thisMonth.getDay() > 0) {
        let lastMonth = new Date(inpYear, inpMonth - 1, 0)
        let lastDayArr = Array.from({length: thisMonth.getDay()}, (e, i) => {
          return {
            year: lastMonth.getFullYear(),
            month: lastMonth.getMonth(),
            date: lastMonth.getDate() - i,
            isHoliday: (lastMonth.getDay() - i) % 7 === 0,
            isHolidaySaturday: (lastMonth.getDay() - i) % 7 === 6,
            data: {}
          }
        })
        lastDayArr = lastDayArr.sort((a, b) => a.date - b.date)
        this.dayArr = this.dayArr.concat(lastDayArr)
      }
      // 현재달
      const lastDate = new Date(helpers.getLastDayOfMonth(inpYear, inpMonth))
      const lastDay = lastDate.getDate()
      let thisDayArr = Array.from({length: lastDay}, (e, i) => {
        return {
          year: thisMonth.getFullYear(),
          month: thisMonth.getMonth(),
          date: i + 1,
          isHoliday: (thisMonth.getDay() + i) % 7 === 0,
          isHolidaySaturday: (thisMonth.getDay() + i) % 7 === 6,
          data: {}
        }
      })
      this.dayArr = this.dayArr.concat(thisDayArr)
      // 다음달
      if (lastDate.getDay() != 6) {
        let nextDayArr = Array.from({length: 6 - lastDate.getDay()}, (e, i) => {
          return {
            year: thisMonth.getFullYear(),
            month: thisMonth.getMonth() + 1,
            date: i + 1,
            isHoliday: false,
            isHolidaySaturday: (lastDate.getDay() + i) % 7 === 6,
            data: {}
          }
        })
        this.dayArr = this.dayArr.concat(nextDayArr)
      }
      console.log(this.dayArr)
      this.totalWeek = Math.floor(this.dayArr.length / 7)
    },
    selectDay (item) {
      this.selectYear = item.year
      this.selectMonth = helpers.fillStr(2, String(item.month + 1) , '0')
      this.selectDate = helpers.fillStr(2, String(item.date), '0')
    },
    isSelect (item) {
      return (this.selectYear === item.year)
        && (this.selectMonth === helpers.fillStr(2, String(item.month+ 1), '0'))
        && (this.selectDate === helpers.fillStr(2, String(item.date), '0'))
    }
  }
}
</script>

<style scoped>
thead > tr {
  background-color: #00acc1;
}
thead > tr > td {
  border: 1px solid black;
  width: 110px;
  text-align: center;
}
tbody > tr > td {
  border: 1px solid black;
  height: 110px;
  vertical-align: top;
}
.scheduler-day {
  vertical-align: top;
  align-content: center;
}
.holiday {
  color: red;
}
.holidaySaturday {
  color: #0d47a1;
}
.active {
  color: #00c853;
}
</style>
