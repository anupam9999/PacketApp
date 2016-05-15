# performance_eval.py

import numpy as np
import matplotlib.pyplot as myplot

http_plt_rtt_list = [146, 352, 277, 210, 65, 71, 1051, 105, 194, 203, 257, 254, 800, 335]
spdy_plt_rtt_list = [6, 134, 23, 66, 4, 66, 4, 26, 60, 4, 12, 14, 387, 101]
quic_plt_rtt_list = [14, 48, 21, 32, 38, 16, 13, 18, 13, 38, 13, 26, 27, 47]

http_plt_throughput_list = [132.31, 34.16, 518.25, 56.62, 36.87, 1.65, 69.2, 24.69, 41.29, 2.34, 29, 7.41, 108.68]
spdy_plt_throughput_list = [219.73, 19.58, 136.33, 38.04, 426.13, 38.04, 368.17, 794.58, 17.63, 1157.21, 58.72, 2461.2, 131.85, 273.34]
quic_plt_throughput_list = [228.93, 139.27, 15.39, 92.48, 193.1, 608.75, 705.17, 515.42, 212.99, 91.39, 26.15, 196.68, 6.23, 508.35]

http_plt_latency_list = [974, 2413, 2008, 472, 134, 104, 5093, 30, 277, 2576, 2356, 2269, 7120, 82]
spdy_plt_latency_list = [22, 269, 46, 77, 67, 77, 77, 137, 300, 199, 439, 283, 1312, 1525]
quic_plt_latency_list = [56, 1097, 1119, 63, 497, 513, 438, 624, 68, 2909, 1173, 287, 1146, 2324]

http_vlt_rtt_list = [177, 129, 297, 252, 103, 261, 169, 355, 213, 312]
spdy_vlt_rtt_list = [126, 154, 266, 249, 98, 186, 154, 349, 134, 204]
quic_vlt_rtt_list = [94, 58, 102, 215, 61, 95, 70, 39, 162, 98]

http_vlt_throughput_list = [15.58, 20.55, 9.04, 10.65, 18.97, 12.43, 12.44, 8.31, 12.35, 8.89]
spdy_vlt_throughput_list = [35.25, 21.54, 14.2, 17.72, 25.93, 18.13, 19.42, 16.13, 27.75, 15.38]
quic_vlt_throughput_list = [369.31, 847.96, 529.4, 146.11, 374.63, 268.42, 349.37, 563.3, 139.05, 381.84]

http_vlt_latency_list = [1768, 1328, 2677, 2019, 1569, 2009, 1696, 2906, 1709, 2498]
spdy_vlt_latency_list = [1664, 2309, 3159, 2637, 1914, 3299, 2642, 4317, 2003, 2590]
quic_vlt_latency_list = [964, 1415, 888, 1070, 732, 1113, 1124, 1093, 1334, 1083]

def plotCDFgraph(data_list1, data_list2, data_list3, title):
    data_list1.sort()
    data_list2.sort()
    data_list3.sort()
    values1 = np.arange(len(data_list1))/float(len(data_list1))
    values2 = np.arange(len(data_list2))/float(len(data_list2))
    values3 = np.arange(len(data_list3))/float(len(data_list3))
    myplot.plot(data_list1, values1, color='blue')
    myplot.plot(data_list2, values2, color='red')
    myplot.plot(data_list3, values3, color='green')
    myplot.title('Performance of HTTP(Blue) vs SPDY(Red) vs Quic(Green)')
    myplot.xlabel(title)
    myplot.ylabel('CDF')
    myplot.show()


if __name__ == '__main__':
    plotCDFgraph(http_plt_rtt_list, spdy_plt_rtt_list, quic_plt_rtt_list, 'Page Load RTT (ms)')
    plotCDFgraph(http_plt_throughput_list, spdy_plt_throughput_list, quic_plt_throughput_list, 'Page Load Throughput (bytes/ms)')
    plotCDFgraph(http_plt_latency_list, spdy_plt_latency_list, quic_plt_latency_list, 'Page Load Latency (ms)')
    plotCDFgraph(http_vlt_rtt_list, spdy_vlt_rtt_list, quic_vlt_rtt_list, 'Video Load RTT (ms)')
    plotCDFgraph(http_vlt_throughput_list, spdy_vlt_throughput_list, quic_vlt_throughput_list, 'Video Load Throughput (bytes/ms)')
    plotCDFgraph(http_vlt_latency_list, spdy_vlt_latency_list, quic_vlt_latency_list, 'Video Load Latency (ms)')

