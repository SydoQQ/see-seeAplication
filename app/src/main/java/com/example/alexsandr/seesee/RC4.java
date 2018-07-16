package com.example.alexsandr.seesee;

public class RC4
{
    public static final int	SKIP_BYTES	= 1024;

    private int				a			= 0;
    private int				b			= 0;
    private final int[]		s			= new int[256];

    public RC4(final byte[] seed)
    {
        if (seed.length < 1) { throw new IllegalArgumentException("RC4 Key too short (minimum: 1 byte)"); }
        if (seed.length > 256) { throw new IllegalArgumentException("RC4 Key too long (maximum: 256 bytes)"); }

        for (int i = 0; i < 256; i++)
        {
            this.s[i] = i;
        }
        for (int i = 0; i < 256; i++)
        {
            this.b = (this.b + this.s[i] + (seed[i % seed.length]) & 0xFF) % 256;
            this.swap(this.b, i);
        }
        this.b = 0;

        for (int i = 0; i < RC4.SKIP_BYTES; i++)
        {
            this.getByte();
        }
    }

    public byte getByte()
    {
        this.a++;
        this.a %= 256;

        this.b += this.s[this.a];
        this.b %= 256;

        this.swap(this.a, this.b);
        return (byte) this.s[(this.s[this.a] + this.s[this.b]) % 256];
    }

    public int getUnsignedInt() {
        int res = ((0xFF & getByte()) << 24) | ((0xFF & getByte()) << 16) | ((0xFF & getByte()) << 8) | (0xFF & getByte());
        return res & 0x0FFFFFFF;
    }

    private final void swap(final int x, final int y)
    {
        final int temp = this.s[x];
        this.s[x] = this.s[y];
        this.s[y] = temp;
    }
}