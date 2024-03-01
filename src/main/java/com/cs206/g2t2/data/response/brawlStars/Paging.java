package com.cs206.g2t2.data.response.brawlStars;

import com.google.gson.annotations.SerializedName;
import lombok.Generated;

public class Paging {
    @SerializedName("cursors")
    private Cursors cursors;

    @Generated
    public Paging() {
    }

    @Generated
    public Cursors getCursors() {
        return this.cursors;
    }

    @Generated
    public void setCursors(Cursors cursors) {
        this.cursors = cursors;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Paging)) {
            return false;
        } else {
            Paging other = (Paging)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$cursors = this.getCursors();
                Object other$cursors = other.getCursors();
                if (this$cursors == null) {
                    if (other$cursors != null) {
                        return false;
                    }
                } else if (!this$cursors.equals(other$cursors)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof Paging;
    }

    @Generated
    public int hashCode() {
        int result = 1;
        Object $cursors = this.getCursors();
        result = result * 59 + ($cursors == null ? 43 : $cursors.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "PaginationResponse.Paging(cursors=" + this.getCursors() + ")";
    }

    public static class Cursors {
        @SerializedName("after")
        private String after;
        @SerializedName("before")
        private String before;

        @Generated
        public Cursors() {
        }

        @Generated
        public String getAfter() {
            return this.after;
        }

        @Generated
        public String getBefore() {
            return this.before;
        }

        @Generated
        public void setAfter(String after) {
            this.after = after;
        }

        @Generated
        public void setBefore(String before) {
            this.before = before;
        }

        @Generated
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof Cursors)) {
                return false;
            } else {
                Cursors other = (Cursors)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$after = this.getAfter();
                    Object other$after = other.getAfter();
                    if (this$after == null) {
                        if (other$after != null) {
                            return false;
                        }
                    } else if (!this$after.equals(other$after)) {
                        return false;
                    }

                    Object this$before = this.getBefore();
                    Object other$before = other.getBefore();
                    if (this$before == null) {
                        if (other$before != null) {
                            return false;
                        }
                    } else if (!this$before.equals(other$before)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        @Generated
        protected boolean canEqual(Object other) {
            return other instanceof Cursors;
        }

        @Generated
        public int hashCode() {
            int result = 1;
            Object $after = this.getAfter();
            result = result * 59 + ($after == null ? 43 : $after.hashCode());
            Object $before = this.getBefore();
            result = result * 59 + ($before == null ? 43 : $before.hashCode());
            return result;
        }

        @Generated
        public String toString() {
            String var10000 = this.getAfter();
            return "PaginationResponse.Paging.Cursors(after=" + var10000 + ", before=" + this.getBefore() + ")";
        }
    }
}
