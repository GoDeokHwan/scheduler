<template>
  <v-app style="overflow: auto">
    <div style="height: 100%; display: inline-block; width: 45%;">
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
                    <span class="scheduler-holiday">{{itemDay.holiday.name}}</span>
                  </div>
                </td>
              </template>
            </tr>
            </tbody>
          </table>
        </div>
      </v-sheet>
    </div>
    <div style="display: inline-block; position: absolute; width: 55%; left: 45%;">
      <v-sheet height="325" color="white"
               elevation="7"
               style="top: 50px;
               position: relative;
               margin-right: 20px">
        <h1>공휴일 등록/수정</h1>
        <div>
          <div class="holiday-div">
            <h3>{{selectYear}} - {{selectMonth}} - {{selectDate}}</h3>
          </div>
          <div class="holiday-div">
            <p class="holiday-p">공휴일명</p>
            <input type="text" placeholder="공휴일명" style="border-style: double;" class="holiday-input" :readonly="holiday.isCommon" v-model="holiday.name"/>
          </div>
          <div class="holiday-div">
            <p class="holiday-p">공휴일 내용</p>
            <textarea rows="3" cols="100" style="border-style: double;" :readonly="holiday.isCommon" v-model="holiday.memo"></textarea>
          </div>
          <div class="holiday-div">
            <v-btn color="primary" style="width: 30%; cursor: pointer;" :disabled="holiday.isCommon" v-on:click="saveHoliday">
              저장
            </v-btn>
          </div>
        </div>
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
import axios from 'axios'

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
      totalWeek: 0,
      userId: 1,
      holiday: {
        name: null,
        memo: null,
        isUpate: false,
        id: null,
        isCommon: false
      }
    }
  },
  created () {
    let date = new Date()
    this.selectYear = date.getFullYear()
    this.selectMonth = helpers.fillStr(2, String(date.getMonth() + 1), '0')
    this.selectDate = helpers.fillStr(2, String(date.getDate()), '0')
    this.setDayArr(date.getFullYear(), date.getMonth() + 1)
    this.getHoliday(this.selectYear, this.selectMonth)
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
            fullDate: lastMonth.getFullYear() + helpers.fillStr(2, String(lastMonth.getMonth() + 1), '0') + helpers.fillStr(2, String(lastMonth.getDate() - i), '0'),
            isHoliday: (lastMonth.getDay() - i) % 7 === 0,
            isHolidaySaturday: (lastMonth.getDay() - i) % 7 === 6,
            holiday: {
              name: null,
              memo: null,
              isUpate: false,
              id: null,
              isCommon: false
            },
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
          fullDate: thisMonth.getFullYear() + helpers.fillStr(2, String(thisMonth.getMonth() + 1), '0') + helpers.fillStr(2, String(i + 1), '0'),
          isHoliday: (thisMonth.getDay() + i) % 7 === 0,
          isHolidaySaturday: (thisMonth.getDay() + i) % 7 === 6,
          holiday: {
            name: null,
            memo: null,
            isUpate: false,
            id: null,
            isCommon: false
          },
          data: {}
        }
      })
      this.dayArr = this.dayArr.concat(thisDayArr)
      // 다음달
      if (lastDate.getDay() != 6) {
        console.log(lastDate.getDay())
        let nextDayArr = Array.from({length: 6 - lastDate.getDay()}, (e, i) => {
          return {
            year: thisMonth.getFullYear(),
            month: thisMonth.getMonth() + 1,
            date: i + 1,
            fullDate: thisMonth.getFullYear() + helpers.fillStr(2, String(thisMonth.getMonth() + 1), '0') + helpers.fillStr(2, String(i + 1), '0'),
            isHoliday: false,
            isHolidaySaturday: (lastDate.getDay() + i + 1) % 7 === 6,
            holiday: {
              name: null,
              memo: null,
              isUpate: false,
              id: null,
              isCommon: false
            },
            data: {}
          }
        })
        this.dayArr = this.dayArr.concat(nextDayArr)
      }
      this.totalWeek = Math.floor(this.dayArr.length / 7)
    },
    selectDay (item) {
      this.selectYear = item.year
      this.selectMonth = helpers.fillStr(2, String(item.month + 1) , '0')
      this.selectDate = helpers.fillStr(2, String(item.date), '0')

      this.holiday = item.holiday
    },
    isSelect (item) {
      return (this.selectYear === item.year)
        && (this.selectMonth === helpers.fillStr(2, String(item.month+ 1), '0'))
        && (this.selectDate === helpers.fillStr(2, String(item.date), '0'))
    },
    getHoliday (inpYear, inpMonth) {
      axios.get(`http://localhost:8180/api/v1/holidays/${this.userId}`, {
        params: {
          year: inpYear,
          month: inpMonth
        }
      }).then(res => {
        if (res.data.code === 0) {
          res.data.data.forEach(fe => {
            this.dayArr.filter(f => f.fullDate === fe.date)
              .map(d => {
                d.isHoliday = true
                d.holiday = {
                  name: fe.name,
                  memo: fe.memo,
                  isUpate: true,
                  id: fe.id,
                  isCommon: fe.isCommon
                }
              })
          })
        } else {
          alert(res.data.message)
        }
      })
      .catch(err => alert(err))
    },
    saveHoliday () {
      if (helpers.isNull(this.holiday.name)) {
        alert('공휴일명은 필수입니다.')
        return
      }

      let holidayRequest = {
        name: this.holiday.name,
        memo: this.holiday.memo,
        dateYear: this.selectYear,
        dateMonth: this.selectMonth,
        dateDay: this.selectDate,
        userId: this.userId
      }
      if (this.holiday.isUpate) {
        axios.put(`http://localhost:8180/api/v1/holidays/${this.holiday.id}`, holidayRequest)
          .then(res => {
            if (res.data.code === 0) {
              let selectDayInfo = this.dayArr.filter(f => f.fullDate === res.data.data.date)
              if (selectDayInfo) {
                selectDayInfo[0]['isHoliday'] = true
                selectDayInfo[0]['holiday'] = {
                  name: res.data.data.name,
                  memo: res.data.data.memo,
                  isUpate: true,
                  id: res.data.data.id,
                  isCommon: res.data.data.isCommon
                }
              }
              alert('수정되었습니다.')
            } else {
              alert(res.data.message)
            }
          }).catch(err => alert(err))
      } else {
        axios.post(`http://localhost:8180/api/v1/holidays`, holidayRequest)
          .then(res => {
            if (res.data.code === 0) {
              let selectDayInfo = this.dayArr.filter(f => f.fullDate === res.data.data.date)
              if (selectDayInfo) {
                selectDayInfo[0]['isHoliday'] = true
                selectDayInfo[0]['holiday'] = {
                  name: res.data.data.name,
                  memo: res.data.data.memo,
                  isUpate: true,
                  id: res.data.data.id,
                  isCommon: res.data.data.isCommon
                }
              }
              alert('저장되었습니다.')
            } else {
              alert(res.data.message)
            }
          }).catch(err => alert(err))

      }
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
.holidaySaturday {
  color: #0d47a1;
}
.holiday {
  color: red;
}
.active {
  color: #00c853;
}
.holiday-div {
  padding-left: 10px;
  padding-right: 10px;
  margin-top: 10px;
  position: relative;
}
.holiday-p {
  margin-bottom: 0px;
  margin-top: 5px;
}
.scheduler-holiday {
  color: red;
}
</style>
