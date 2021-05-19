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
        <div>
          <div style="display: inline-block; overflow: auto; width: 50%; height: 100%; vertical-align: top;">
            <div class="">
              <v-btn color="primary" style="width: 30%; cursor: pointer;" v-on:click="addScheduler">
                추가
              </v-btn>
            </div>
            <table style="width: 90%;">
              <thead>
                <tr>
                  <th width="30%">날짜</th>
                  <th width="70%">내용</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in schedulerDatas" v-on:click="fnSelectScheduler(item)">
                  <td>{{item.dateYear}} - {{item.dateMonth}} - {{item.dateDay}} {{item.timeHour}}:{{item.timeMin}}</td>
                  <td>{{item.memo}}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div style="display: inline-block; width: 50%;">
            <div class="">
              <p></p>
            </div>
            <div class="">
              <p class="">시간</p>
              <select v-model="selectScheduler.hour" style="border-style: double; width: 100px;">
                <option v-for="item in hour" :value="item">{{item}}</option>
              </select>
              &nbsp;
              <select v-model="selectScheduler.min" style="border-style: double;  width: 100px;">
                <option v-for="item in min" :value="item">{{item}}</option>
              </select>
            </div>
            <div class="">
              <p class="">알람여부</p>
              <select v-model="selectScheduler.alermType" style="border-style: double;  width: 100px;">
                <option v-for="item in alarmType" :value="item.code">{{item.name}}</option>
              </select>
              &nbsp;
              <select v-if="selectScheduler.alermType == 'DAY'" v-model="selectScheduler.alermTime" style="border-style: double;  width: 100px;">
                <option v-for="item in day" :value="item">{{item}}</option>
              </select>
              <select v-if="selectScheduler.alermType == 'HOUR'" v-model="selectScheduler.alermTime" style="border-style: double;  width: 100px;">
                <option v-for="item in hour" :value="item">{{item}}</option>
              </select>
              <select v-if="selectScheduler.alermType == 'MINUTE'" v-model="selectScheduler.alermTime" style="border-style: double;  width: 100px;">
                <option v-for="item in min" :value="item">{{item}}</option>
              </select>
            </div>
            <div class="">
              <p class="">반복여부</p>
              <select v-model="selectScheduler.repeatType" style="border-style: double;  width: 100px;">
                <option v-for="item in repeatType" :value="item.code">{{item.name}}</option>
              </select>
            </div>
            <div class="">
              <p class="">공휴일 제외 여부</p>
              <select v-model="selectScheduler.isHoliday" style="border-style: double;  width: 100px;">
                <option value="N">N</option>
                <option value="Y">Y</option>
              </select>
            </div>
            <div class="">
              <p class="">메모</p>
              <textarea rows="3" cols="60" style="border-style: double;" v-model="selectScheduler.memo"></textarea>
            </div>
            <div class="">
              <v-btn color="primary" style="width: 30%; cursor: pointer;" v-on:click="saveScheduler">
                저장
              </v-btn>
            </div>
          </div>
        </div>
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
      },
      schedulerDatas: [],
      selectScheduler: {
        hour: '00',
        min: '00',
        alermType: 'NONE',
        alermTime: '',
        repeatType: 'NONE',
        isHoliday: 'N',
        memo: '',
        isUpdate: false,
        id: null
      },
      hour: ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23'],
      min: ['00','01','02','03','04','05','06','07','08','09',
        '10','11','12','13','14','15','16','17','18','19',
        '20','21','22','23','24','25','26','27','28','29',
        '30','31','32','33','34','35','36','37','38','39',
        '40','41','42','43','44','45','46','47','48','49',
        '50','51','52','53','54','55','56','57','58','59'],
      day: ['1','2','3','4','5','6','7'],
      alarmType: [
        {code : 'NONE', name: '없음'},
        {code : 'DAY', name: '일'},
        {code : 'HOUR', name: '시간'},
        {code : 'MINUTE', name: '분'}
      ],
      repeatType: [
        {code : 'NONE', name: '없음'},
        {code : 'YEAR', name: '년'},
        {code : 'MONTH', name: '달'},
        {code : 'WEAK', name: '주'}
      ]
    }
  },
  created () {
    let date = new Date()
    this.selectYear = String(date.getFullYear())
    this.selectMonth = helpers.fillStr(2, String(date.getMonth() + 1), '0')
    this.selectDate = helpers.fillStr(2, String(date.getDate()), '0')
    this.setDayArr(date.getFullYear(), date.getMonth() + 1)
    this.getHoliday(this.selectYear, this.selectMonth)
    this.getSchedulers(this.selectYear, this.selectMonth)
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
            data: []
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
          data: []
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
            data: []
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
      this.schedulerDatas = item.data
      this.addScheduler()
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
    },
    addScheduler () {
      this.selectScheduler = {
        hour: '00',
        min: '00',
        alermType: 'NONE',
        alermTime: '',
        repeatType: 'NONE',
        isHoliday: 'N',
        memo: '',
        isUpdate: false
      }
    },
    saveScheduler () {
      if (this.selectScheduler.isUpdate) {
        axios.put(`http://localhost:8180/api/v1/schedulers/${this.selectScheduler.id}`, {
          userId: this.userId,
          dateYear: this.selectYear,
          dateMonth: this.selectMonth,
          dateDay: this.selectDate,
          timeHour: this.selectScheduler.hour,
          timeMin: this.selectScheduler.min,
          isAlarm: this.selectScheduler.alermType != 'NONE',
          alarmType: this.selectScheduler.alermType,
          alarmTime: this.selectScheduler.alermTime,
          isRepeat: this.selectScheduler.repeatType != 'NONE',
          repeatType: this.selectScheduler.repeatType,
          isHoliday: this.selectScheduler.isHoliday === 'Y',
          memo: this.selectScheduler.memo
        }).then(res => {
          if (res.data.code === 0) {
            this.dayArr.filter(f => f.fullDate === res.data.data.dateYear + res.data.data.dateMonth + res.data.data.dateDay)
              .map(m => {
                m.data.filter(ff => ff.id === res.data.data.id)
                  .map(mm => mm = res.data.data)
              })
          } else {
            alert(res.data.message)
          }
        }).catch(err => alert(err))
      } else {
        axios.post(`http://localhost:8180/api/v1/scheduler`, {
          userId: this.userId,
          dateYear: this.selectYear,
          dateMonth: this.selectMonth,
          dateDay: this.selectDate,
          timeHour: this.selectScheduler.hour,
          timeMin: this.selectScheduler.min,
          isAlarm: this.selectScheduler.alermType != 'NONE',
          alarmType: this.selectScheduler.alermType,
          alarmTime: this.selectScheduler.alermTime,
          isRepeat: this.selectScheduler.repeatType != 'NONE',
          repeatType: this.selectScheduler.repeatType,
          isHoliday: this.selectScheduler.isHoliday === 'Y',
          memo: this.selectScheduler.memo
        }).then(res => {
          if (res.data.code === 0) {
            this.dayArr.filter(f => f.fullDate === res.data.data.dateYear + res.data.data.dateMonth + res.data.data.dateDay)
              .map(m => {
                m.data.push(res.data.data)
              })
          } else {
            alert(res.data.message)
          }
        }).catch(err => alert(err))
      }
    },
    getSchedulers (inpYear, inpMonth) {
      axios.get(`http://localhost:8180/api/v1/schedulers/${this.userId}`, {
        params: {
          year: inpYear,
          month: inpMonth
        }
      }).then(res => {
        if (res.data.code == 0) {
          res.data.data.forEach(data => {
            this.dayArr.filter(f => f.fullDate === data.dateYear + data.dateMonth + data.dateDay)
              .map(m => {
                m.data.push(data)
              })
          })
          console.log(this.dayArr)
        } else {
          alert(res.data.message)
        }
      }).catch(err => alert(err))
    },
    fnSelectScheduler (item) {
      this.selectScheduler = {
        hour: item.timeHour,
        min: item.timeMin,
        alermType: item.alarmType,
        alermTime: item.alarmTime,
        repeatType: item.repeatType,
        isHoliday: item.isHoliday ? 'Y' : 'N',
        memo: item.memo,
        isUpdate: true,
        id: item.id
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
