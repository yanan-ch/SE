function ans = FFT(N,Fs,c)

dt=1/Fs;

t=[0:N-1]*dt;

xn = 0;
for i=1:size(c)
    xn=xn+cos(2*pi*c(i)*[0:N-1]);
end

subplot(2,1,1);
plot(t,xn);
axis([0 N min(xn') max(xn')]);
xlabel('Time/s'),title('1024 Original points');

f0=1/(dt*N);

f=[0:ceil((N-1)/2)]*f0;

XN=fft(xn,N)/N;

XN=abs(XN);

subplot(2,1,2);
stem( f,2*XN(1:ceil((N-1)/2)+1) );
xlabel('Frequency/Hz');
axis([0 Fs/2 0 max(2*XN(1:ceil((N-1)/2)+1))+0.2]);
title('1024 Execution Point');

end

