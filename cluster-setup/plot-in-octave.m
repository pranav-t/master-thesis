%"workload-distribution"
% cd ~/Desktop/thesis/plots/workload-distribution/hotspotted-data;
% cd ~/Desktop/thesis/plots/workload-distribution/uniform-data;
% load sue-data-1-request-count.metrics;
% load sue-data-2-request-count.metrics;
% load sue-data-3-request-count.metrics;
% load sue-data-4-request-count.metrics;

% metrics_counts = [size(sue_data_1_request_count)(1), size(sue_data_2_request_count)(1), size(sue_data_3_request_count)(1), size(sue_data_4_request_count)(1)];
% cardinality = max(metrics_counts);

% % x-axes data
% time = [0:2:(cardinality*2)]';

% % y-axes data(padded with zeroes)
% pkg load image;
% sue_data_1_request_count = padarray(sue_data_1_request_count, [size(time)(1) - size(sue_data_1_request_count)(1), 0], 'replicate', 'post');
% sue_data_2_request_count = padarray(sue_data_2_request_count, [size(time)(1) - size(sue_data_2_request_count)(1), 0], 'replicate', 'post');
% sue_data_3_request_count = padarray(sue_data_3_request_count, [size(time)(1) - size(sue_data_3_request_count)(1), 0], 'replicate', 'post');
% sue_data_4_request_count = padarray(sue_data_4_request_count, [size(time)(1) - size(sue_data_4_request_count)(1), 0], 'replicate', 'post');

% fig = figure();
% plot(time, sue_data_1_request_count, time, sue_data_2_request_count, time, sue_data_3_request_count, time, sue_data_4_request_count);
% xlabel('Time Elapsed (s)');
% ylabel('Total Requests Received');
% legend('Region Server 1', 'Region Server 2', 'Region Server 3', 'Region Server 4', 'location', 'southeast');
% grid();
% box();
% % print -dpdf ../hotspotted-plot.pdf
% print -dpdf ../uniform-plot.pdf

%"workload-parallelization"
% cd ~/Desktop/thesis/plots/workload-parallelization;
% sue_data_1_2x_metrics = load('2-mappers-data/sue-data-1-request-count.metrics');
% sue_data_2_2x_metrics = load('2-mappers-data/sue-data-2-request-count.metrics');
% sue_data_3_2x_metrics = load('2-mappers-data/sue-data-3-request-count.metrics');
% sue_data_4_2x_metrics = load('2-mappers-data/sue-data-4-request-count.metrics');

% sue_data_1_4x_metrics = load('4-mappers-data/sue-data-1-request-count.metrics');
% sue_data_2_4x_metrics = load('4-mappers-data/sue-data-2-request-count.metrics');
% sue_data_3_4x_metrics = load('4-mappers-data/sue-data-3-request-count.metrics');
% sue_data_4_4x_metrics = load('4-mappers-data/sue-data-4-request-count.metrics');

% sue_data_1_8x_metrics = load('8-mappers-data/sue-data-1-request-count.metrics');
% sue_data_2_8x_metrics = load('8-mappers-data/sue-data-2-request-count.metrics');
% sue_data_3_8x_metrics = load('8-mappers-data/sue-data-3-request-count.metrics');
% sue_data_4_8x_metrics = load('8-mappers-data/sue-data-4-request-count.metrics');

% sue_data_1_16x_metrics = load('16-mappers-data/sue-data-1-request-count.metrics');
% sue_data_2_16x_metrics = load('16-mappers-data/sue-data-2-request-count.metrics');
% sue_data_3_16x_metrics = load('16-mappers-data/sue-data-3-request-count.metrics');
% sue_data_4_16x_metrics = load('16-mappers-data/sue-data-4-request-count.metrics');

% sue_data_1_32x_metrics = load('32-mappers-data/sue-data-1-request-count.metrics');
% sue_data_2_32x_metrics = load('32-mappers-data/sue-data-2-request-count.metrics');
% sue_data_3_32x_metrics = load('32-mappers-data/sue-data-3-request-count.metrics');
% sue_data_4_32x_metrics = load('32-mappers-data/sue-data-4-request-count.metrics');

% sue_data_1_64x_metrics = load('64-mappers-data/sue-data-1-request-count.metrics');
% sue_data_2_64x_metrics = load('64-mappers-data/sue-data-2-request-count.metrics');
% sue_data_3_64x_metrics = load('64-mappers-data/sue-data-3-request-count.metrics');
% sue_data_4_64x_metrics = load('64-mappers-data/sue-data-4-request-count.metrics');


% maxsizefor2x = max([size(sue_data_1_2x_metrics)(1), size(sue_data_2_2x_metrics)(1), size(sue_data_3_2x_metrics)(1), size(sue_data_4_2x_metrics)(1)]);
% maxsizefor4x = max([size(sue_data_1_4x_metrics)(1), size(sue_data_2_4x_metrics)(1), size(sue_data_3_4x_metrics)(1), size(sue_data_4_4x_metrics)(1)]);
% maxsizefor8x = max([size(sue_data_1_8x_metrics)(1), size(sue_data_2_8x_metrics)(1), size(sue_data_3_8x_metrics)(1), size(sue_data_4_8x_metrics)(1)]);
% maxsizefor16x = max([size(sue_data_1_16x_metrics)(1), size(sue_data_2_16x_metrics)(1), size(sue_data_3_16x_metrics)(1), size(sue_data_4_16x_metrics)(1)]);
% maxsizefor32x = max([size(sue_data_1_32x_metrics)(1), size(sue_data_2_32x_metrics)(1), size(sue_data_3_32x_metrics)(1), size(sue_data_4_32x_metrics)(1)]);
% maxsizefor64x = max([size(sue_data_1_64x_metrics)(1), size(sue_data_2_64x_metrics)(1), size(sue_data_3_64x_metrics)(1), size(sue_data_4_64x_metrics)(1)]);

% maxforxaxis = max([maxsizefor2x, maxsizefor4x, maxsizefor8x, maxsizefor16x, maxsizefor32x, maxsizefor64x]);

% % x-axes data
% time = [0:2:(maxforxaxis*2)]';

% % y-axes data(padded with last value	)
% pkg load image;

% sue_data_1_2x_metrics = padarray(sue_data_1_2x_metrics, [size(time)(1) - size(sue_data_1_2x_metrics)(1), 0], 'replicate', 'post');
% sue_data_2_2x_metrics = padarray(sue_data_2_2x_metrics, [size(time)(1) - size(sue_data_2_2x_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_2x_metrics = padarray(sue_data_3_2x_metrics, [size(time)(1) - size(sue_data_3_2x_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_2x_metrics = padarray(sue_data_4_2x_metrics, [size(time)(1) - size(sue_data_4_2x_metrics)(1), 0], 'replicate', 'post');

% sue_data_1_4x_metrics = padarray(sue_data_1_4x_metrics, [size(time)(1) - size(sue_data_1_4x_metrics)(1), 0], 'replicate', 'post');
% sue_data_2_4x_metrics = padarray(sue_data_2_4x_metrics, [size(time)(1) - size(sue_data_2_4x_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_4x_metrics = padarray(sue_data_3_4x_metrics, [size(time)(1) - size(sue_data_3_4x_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_4x_metrics = padarray(sue_data_4_4x_metrics, [size(time)(1) - size(sue_data_4_4x_metrics)(1), 0], 'replicate', 'post');

% sue_data_1_8x_metrics = padarray(sue_data_1_8x_metrics, [size(time)(1) - size(sue_data_1_8x_metrics)(1), 0], 'replicate', 'post');
% sue_data_2_8x_metrics = padarray(sue_data_2_8x_metrics, [size(time)(1) - size(sue_data_2_8x_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_8x_metrics = padarray(sue_data_3_8x_metrics, [size(time)(1) - size(sue_data_3_8x_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_8x_metrics = padarray(sue_data_4_8x_metrics, [size(time)(1) - size(sue_data_4_8x_metrics)(1), 0], 'replicate', 'post');

% sue_data_1_16x_metrics = padarray(sue_data_1_16x_metrics, [size(time)(1) - size(sue_data_1_16x_metrics)(1), 0], 'replicate', 'post');
% sue_data_2_16x_metrics = padarray(sue_data_2_16x_metrics, [size(time)(1) - size(sue_data_2_16x_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_16x_metrics = padarray(sue_data_3_16x_metrics, [size(time)(1) - size(sue_data_3_16x_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_16x_metrics = padarray(sue_data_4_16x_metrics, [size(time)(1) - size(sue_data_4_16x_metrics)(1), 0], 'replicate', 'post');

% sue_data_1_32x_metrics =  padarray(sue_data_1_32x_metrics, [size(time)(1) - size(sue_data_1_32x_metrics)(1)], 'replicate', 'post');
% sue_data_2_32x_metrics =  padarray(sue_data_2_32x_metrics, [size(time)(1) - size(sue_data_2_32x_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_32x_metrics =  padarray(sue_data_3_32x_metrics, [size(time)(1) - size(sue_data_3_32x_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_32x_metrics =  padarray(sue_data_4_32x_metrics, [size(time)(1) - size(sue_data_4_32x_metrics)(1), 0], 'replicate', 'post');

% sue_data_1_64x_metrics = padarray(sue_data_1_64x_metrics, [size(time)(1) - size(sue_data_1_64x_metrics)(1), 0], 'replicate', 'post');
% sue_data_2_64x_metrics = padarray(sue_data_2_64x_metrics, [size(time)(1) - size(sue_data_2_64x_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_64x_metrics = padarray(sue_data_3_64x_metrics, [size(time)(1) - size(sue_data_3_64x_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_64x_metrics = padarray(sue_data_4_64x_metrics, [size(time)(1) - size(sue_data_4_64x_metrics)(1), 0], 'replicate', 'post');

% sue_data_2x = sue_data_1_2x_metrics + sue_data_2_2x_metrics + sue_data_3_2x_metrics + sue_data_4_2x_metrics;
% sue_data_4x = sue_data_1_4x_metrics + sue_data_2_4x_metrics + sue_data_3_4x_metrics + sue_data_4_4x_metrics;
% sue_data_8x = sue_data_1_8x_metrics + sue_data_2_8x_metrics + sue_data_3_8x_metrics + sue_data_4_8x_metrics;
% sue_data_16x = sue_data_1_16x_metrics + sue_data_2_16x_metrics + sue_data_3_16x_metrics + sue_data_4_16x_metrics;
% sue_data_32x = sue_data_1_32x_metrics + sue_data_2_32x_metrics + sue_data_3_32x_metrics + sue_data_4_32x_metrics;
% sue_data_64x = sue_data_1_64x_metrics + sue_data_2_64x_metrics + sue_data_3_64x_metrics + sue_data_4_64x_metrics;

% fig = figure();
% plot(time, sue_data_2x, time, sue_data_4x, time, sue_data_8x, time, sue_data_16x, time, sue_data_32x, time, sue_data_64x);
% xlabel('Time Elapsed (s)');
% ylabel('Total Requests Received');
% legend('2 Map Tasks', '4 Map Tasks', '8 Map Tasks', '16 Map Tasks', '32 Map Tasks', '64 Map Tasks', 'location', 'southeast');
% grid();
% box();
% print -dpdf workload-parallelization-progress-plot.pdf

% cd ~/Desktop/thesis/plots/workload-parallelization;
% fig = figure();
% plot([2, 4, 8, 16, 32, 64], [2200, 1150, 580, 450, 470, 1150], 'marker', 'o', 'markersize', 10);
% xlabel('Mapper Count');
% ylabel('Total Load Time (s)');
% grid();
% box();


%"workload-granularity"
cd ~/Desktop/thesis/plots/workload-granularity;
% sue_data_1_a_metrics = load('0.2mb-parts-data/sue-data-1-request-count.metrics');
% sue_data_2_a_metrics = load('0.2mb-parts-data/sue-data-2-request-count.metrics');
% sue_data_3_a_metrics = load('0.2mb-parts-data/sue-data-3-request-count.metrics');
% sue_data_4_a_metrics = load('0.2mb-parts-data/sue-data-4-request-count.metrics');

% sue_data_1_b_metrics = load('0.4mb-parts-data/sue-data-1-request-count.metrics');
% sue_data_2_b_metrics = load('0.4mb-parts-data/sue-data-2-request-count.metrics');
% sue_data_3_b_metrics = load('0.4mb-parts-data/sue-data-3-request-count.metrics');
% sue_data_4_b_metrics = load('0.4mb-parts-data/sue-data-4-request-count.metrics');

% sue_data_1_c_metrics = load('0.8mb-parts-data/sue-data-1-request-count.metrics');
% sue_data_2_c_metrics = load('0.8mb-parts-data/sue-data-2-request-count.metrics');
% sue_data_3_c_metrics = load('0.8mb-parts-data/sue-data-3-request-count.metrics');
% sue_data_4_c_metrics = load('0.8mb-parts-data/sue-data-4-request-count.metrics');

% sue_data_1_d_metrics = load('1.6mb-parts-data/sue-data-1-request-count.metrics');
% sue_data_2_d_metrics = load('1.6mb-parts-data/sue-data-2-request-count.metrics');
% sue_data_3_d_metrics = load('1.6mb-parts-data/sue-data-3-request-count.metrics');
% sue_data_4_d_metrics = load('1.6mb-parts-data/sue-data-4-request-count.metrics');

% sue_data_1_e_metrics = load('3.2mb-parts-data/sue-data-1-request-count.metrics');
% sue_data_2_e_metrics = load('3.2mb-parts-data/sue-data-2-request-count.metrics');
% sue_data_3_e_metrics = load('3.2mb-parts-data/sue-data-3-request-count.metrics');
% sue_data_4_e_metrics = load('3.2mb-parts-data/sue-data-4-request-count.metrics');

% % sue_data_1_f_metrics = load('1.2mb-parts-data/sue-data-1-request-count.metrics');
% % sue_data_2_f_metrics = load('1.2mb-parts-data/sue-data-2-request-count.metrics');
% % sue_data_3_f_metrics = load('1.2mb-parts-data/sue-data-3-request-count.metrics');
% % sue_data_4_f_metrics = load('1.2mb-parts-data/sue-data-4-request-count.metrics');

% % sue_data_1_g_metrics = load('1.4mb-parts-data/sue-data-1-request-count.metrics');
% % sue_data_2_g_metrics = load('1.4mb-parts-data/sue-data-2-request-count.metrics');
% % sue_data_3_g_metrics = load('1.4mb-parts-data/sue-data-3-request-count.metrics');
% % sue_data_4_g_metrics = load('1.4mb-parts-data/sue-data-4-request-count.metrics');

% % sue_data_1_h_metrics = load('1.6mb-parts-data/sue-data-1-request-count.metrics');
% % sue_data_2_h_metrics = load('1.6mb-parts-data/sue-data-2-request-count.metrics');
% % sue_data_3_h_metrics = load('1.6mb-parts-data/sue-data-3-request-count.metrics');
% % sue_data_4_h_metrics = load('1.6mb-parts-data/sue-data-4-request-count.metrics');

% % sue_data_1_i_metrics = load('1.8mb-parts-data/sue-data-1-request-count.metrics');
% % sue_data_2_i_metrics = load('1.8mb-parts-data/sue-data-2-request-count.metrics');
% % sue_data_3_i_metrics = load('1.8mb-parts-data/sue-data-3-request-count.metrics');
% % sue_data_4_i_metrics = load('1.8mb-parts-data/sue-data-4-request-count.metrics');

% % sue_data_1_j_metrics = load('2.0mb-parts-data/sue-data-1-request-count.metrics');
% % sue_data_2_j_metrics = load('2.0mb-parts-data/sue-data-2-request-count.metrics');
% % sue_data_3_j_metrics = load('2.0mb-parts-data/sue-data-3-request-count.metrics');
% % sue_data_4_j_metrics = load('2.0mb-parts-data/sue-data-4-request-count.metrics');


% maxsizefora = max([size(sue_data_1_a_metrics)(1), size(sue_data_2_a_metrics)(1), size(sue_data_3_a_metrics)(1), size(sue_data_4_a_metrics)(1)]);
% maxsizeforb = max([size(sue_data_1_b_metrics)(1), size(sue_data_2_b_metrics)(1), size(sue_data_3_b_metrics)(1), size(sue_data_4_b_metrics)(1)]);
% maxsizeforc = max([size(sue_data_1_c_metrics)(1), size(sue_data_2_c_metrics)(1), size(sue_data_3_c_metrics)(1), size(sue_data_4_c_metrics)(1)]);
% maxsizeford = max([size(sue_data_1_d_metrics)(1), size(sue_data_2_d_metrics)(1), size(sue_data_3_d_metrics)(1), size(sue_data_4_d_metrics)(1)]);
% maxsizefore = max([size(sue_data_1_e_metrics)(1), size(sue_data_2_e_metrics)(1), size(sue_data_3_e_metrics)(1), size(sue_data_4_e_metrics)(1)]);
% % maxsizeforf = max([size(sue_data_1_f_metrics)(1), size(sue_data_2_f_metrics)(1), size(sue_data_3_f_metrics)(1), size(sue_data_4_f_metrics)(1)]);
% % maxsizeforg = max([size(sue_data_1_g_metrics)(1), size(sue_data_2_g_metrics)(1), size(sue_data_3_g_metrics)(1), size(sue_data_4_g_metrics)(1)]);
% % maxsizeforh = max([size(sue_data_1_h_metrics)(1), size(sue_data_2_h_metrics)(1), size(sue_data_3_h_metrics)(1), size(sue_data_4_h_metrics)(1)]);
% % maxsizefori = max([size(sue_data_1_i_metrics)(1), size(sue_data_2_i_metrics)(1), size(sue_data_3_i_metrics)(1), size(sue_data_4_i_metrics)(1)]);
% % maxsizeforj = max([size(sue_data_1_j_metrics)(1), size(sue_data_2_j_metrics)(1), size(sue_data_3_j_metrics)(1), size(sue_data_4_j_metrics)(1)]);

% % maxforxaxis = max([maxsizefora, maxsizeforb, maxsizeforc, maxsizeford, maxsizefore, maxsizeforf, maxsizeforg, maxsizeforh, maxsizefori, maxsizeforj]);
% maxforxaxis = max([maxsizefora, maxsizeforb, maxsizeforc, maxsizeford, maxsizefore]);

% % x-axes data
% time = [0:2:(maxforxaxis*2)]';

% % y-axes data(padded with last value	)
% pkg load image;

% sue_data_1_a_metrics = padarray(sue_data_1_a_metrics, [size(time)(1) - size(sue_data_1_a_metrics)(1), 0], 'replicate', 'post');
% sue_data_2_a_metrics = padarray(sue_data_2_a_metrics, [size(time)(1) - size(sue_data_2_a_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_a_metrics = padarray(sue_data_3_a_metrics, [size(time)(1) - size(sue_data_3_a_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_a_metrics = padarray(sue_data_4_a_metrics, [size(time)(1) - size(sue_data_4_a_metrics)(1), 0], 'replicate', 'post');

% sue_data_1_b_metrics = padarray(sue_data_1_b_metrics, [size(time)(1) - size(sue_data_1_b_metrics)(1), 0], 'replicate', 'post');
% sue_data_2_b_metrics = padarray(sue_data_2_b_metrics, [size(time)(1) - size(sue_data_2_b_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_b_metrics = padarray(sue_data_3_b_metrics, [size(time)(1) - size(sue_data_3_b_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_b_metrics = padarray(sue_data_4_b_metrics, [size(time)(1) - size(sue_data_4_b_metrics)(1), 0], 'replicate', 'post');

% sue_data_1_c_metrics = padarray(sue_data_1_c_metrics, [size(time)(1) - size(sue_data_1_c_metrics)(1), 0], 'replicate', 'post');
% sue_data_2_c_metrics = padarray(sue_data_2_c_metrics, [size(time)(1) - size(sue_data_2_c_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_c_metrics = padarray(sue_data_3_c_metrics, [size(time)(1) - size(sue_data_3_c_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_c_metrics = padarray(sue_data_4_c_metrics, [size(time)(1) - size(sue_data_4_c_metrics)(1), 0], 'replicate', 'post');

% sue_data_1_d_metrics = padarray(sue_data_1_d_metrics, [size(time)(1) - size(sue_data_1_d_metrics)(1), 0], 'replicate', 'post');
% sue_data_2_d_metrics = padarray(sue_data_2_d_metrics, [size(time)(1) - size(sue_data_2_d_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_d_metrics = padarray(sue_data_3_d_metrics, [size(time)(1) - size(sue_data_3_d_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_d_metrics = padarray(sue_data_4_d_metrics, [size(time)(1) - size(sue_data_4_d_metrics)(1), 0], 'replicate', 'post');

% sue_data_1_e_metrics = padarray(sue_data_1_e_metrics, [size(time)(1) - size(sue_data_1_e_metrics)(1), 0], 'replicate', 'post');
% sue_data_2_e_metrics = padarray(sue_data_2_e_metrics, [size(time)(1) - size(sue_data_2_e_metrics)(1), 0], 'replicate', 'post');
% sue_data_3_e_metrics = padarray(sue_data_3_e_metrics, [size(time)(1) - size(sue_data_3_e_metrics)(1), 0], 'replicate', 'post');
% sue_data_4_e_metrics = padarray(sue_data_4_e_metrics, [size(time)(1) - size(sue_data_4_e_metrics)(1), 0], 'replicate', 'post');

% % sue_data_1_f_metrics = padarray(sue_data_1_f_metrics, [size(time)(1) - size(sue_data_1_f_metrics)(1), 0], 'replicate', 'post');
% % sue_data_2_f_metrics = padarray(sue_data_2_f_metrics, [size(time)(1) - size(sue_data_2_f_metrics)(1), 0], 'replicate', 'post');
% % sue_data_3_f_metrics = padarray(sue_data_3_f_metrics, [size(time)(1) - size(sue_data_3_f_metrics)(1), 0], 'replicate', 'post');
% % sue_data_4_f_metrics = padarray(sue_data_4_f_metrics, [size(time)(1) - size(sue_data_4_f_metrics)(1), 0], 'replicate', 'post');

% % sue_data_1_g_metrics = padarray(sue_data_1_g_metrics, [size(time)(1) - size(sue_data_1_g_metrics)(1), 0], 'replicate', 'post');
% % sue_data_2_g_metrics = padarray(sue_data_2_g_metrics, [size(time)(1) - size(sue_data_2_g_metrics)(1), 0], 'replicate', 'post');
% % sue_data_3_g_metrics = padarray(sue_data_3_g_metrics, [size(time)(1) - size(sue_data_3_g_metrics)(1), 0], 'replicate', 'post');
% % sue_data_4_g_metrics = padarray(sue_data_4_g_metrics, [size(time)(1) - size(sue_data_4_g_metrics)(1), 0], 'replicate', 'post');

% % sue_data_1_h_metrics = padarray(sue_data_1_h_metrics, [size(time)(1) - size(sue_data_1_h_metrics)(1), 0], 'replicate', 'post');
% % sue_data_2_h_metrics = padarray(sue_data_2_h_metrics, [size(time)(1) - size(sue_data_2_h_metrics)(1), 0], 'replicate', 'post');
% % sue_data_3_h_metrics = padarray(sue_data_3_h_metrics, [size(time)(1) - size(sue_data_3_h_metrics)(1), 0], 'replicate', 'post');
% % sue_data_4_h_metrics = padarray(sue_data_4_h_metrics, [size(time)(1) - size(sue_data_4_h_metrics)(1), 0], 'replicate', 'post');

% % sue_data_1_i_metrics = padarray(sue_data_1_i_metrics, [size(time)(1) - size(sue_data_1_i_metrics)(1), 0], 'replicate', 'post');
% % sue_data_2_i_metrics = padarray(sue_data_2_i_metrics, [size(time)(1) - size(sue_data_2_i_metrics)(1), 0], 'replicate', 'post');
% % sue_data_3_i_metrics = padarray(sue_data_3_i_metrics, [size(time)(1) - size(sue_data_3_i_metrics)(1), 0], 'replicate', 'post');
% % sue_data_4_i_metrics = padarray(sue_data_4_i_metrics, [size(time)(1) - size(sue_data_4_i_metrics)(1), 0], 'replicate', 'post');

% % sue_data_1_j_metrics = padarray(sue_data_1_j_metrics, [size(time)(1) - size(sue_data_1_j_metrics)(1), 0], 'replicate', 'post');
% % sue_data_2_j_metrics = padarray(sue_data_2_j_metrics, [size(time)(1) - size(sue_data_2_j_metrics)(1), 0], 'replicate', 'post');
% % sue_data_3_j_metrics = padarray(sue_data_3_j_metrics, [size(time)(1) - size(sue_data_3_j_metrics)(1), 0], 'replicate', 'post');
% % sue_data_4_j_metrics = padarray(sue_data_4_j_metrics, [size(time)(1) - size(sue_data_4_j_metrics)(1), 0], 'replicate', 'post');


% sue_data_a = sue_data_1_a_metrics + sue_data_2_a_metrics + sue_data_3_a_metrics + sue_data_4_a_metrics;
% sue_data_b = sue_data_1_b_metrics + sue_data_2_b_metrics + sue_data_3_b_metrics + sue_data_4_b_metrics;
% sue_data_c = sue_data_1_c_metrics + sue_data_2_c_metrics + sue_data_3_c_metrics + sue_data_4_c_metrics;

% sue_data_d = sue_data_1_d_metrics + sue_data_2_d_metrics + sue_data_3_d_metrics + sue_data_4_d_metrics;
% sue_data_e = sue_data_1_e_metrics + sue_data_2_e_metrics + sue_data_3_e_metrics + sue_data_4_e_metrics;
% % sue_data_f = sue_data_1_f_metrics + sue_data_2_f_metrics + sue_data_3_f_metrics + sue_data_4_f_metrics;

% % sue_data_g = sue_data_1_g_metrics + sue_data_2_g_metrics + sue_data_3_g_metrics + sue_data_4_g_metrics;
% % sue_data_h = sue_data_1_h_metrics + sue_data_2_h_metrics + sue_data_3_h_metrics + sue_data_4_h_metrics;
% % sue_data_i = sue_data_1_i_metrics + sue_data_2_i_metrics + sue_data_3_i_metrics + sue_data_4_i_metrics;

% % sue_data_j = sue_data_1_j_metrics + sue_data_2_j_metrics + sue_data_3_j_metrics + sue_data_4_j_metrics;


% fig = figure();
% % plot(time, sue_data_a, time, sue_data_b, time, sue_data_c, time, sue_data_d, time, sue_data_e, time, sue_data_f, time, sue_data_g, time, sue_data_h, time, sue_data_i, time, sue_data_j);
% plot(time, sue_data_a, time, sue_data_b, time, sue_data_c, time, sue_data_d, time, sue_data_e);
% xlabel('Time Elapsed (s)');
% ylabel('Total Requests Received');
% legend('0.2 MB', '0.4 MB', '0.8 MB', '1.6 MB', '3.2 MB', 'location', 'southeast');
% grid();
% box();
% print -dpdf workload-granularity-progress-plot.pdf


% fig = figure();
% % plot(time, sue_data_a, time, sue_data_b, time, sue_data_c, time, sue_data_d, time, sue_data_e, time, sue_data_f, time, sue_data_g, time, sue_data_h, time, sue_data_i, time, sue_data_j);
% plot([0.2, 0.4, 0.8, 1.6, 3.2],[400, 330, 325, 310, 310],'marker', 'o', 'markersize', 10);
% xlabel('Part File Size (MB)');
% ylabel('Total Load Time (s)');
% grid();
% box();
% print -dpdf workload-granularity-timings-plot.pdf